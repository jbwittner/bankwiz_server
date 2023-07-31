package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.GroupUpdateRequest;
import fr.bankwiz.openapi.model.UpdateUserGroupRequest;
import fr.bankwiz.server.dto.GroupDTOBuilder;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.exception.UserNoAccessGroupException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.GroupRepository;
import fr.bankwiz.server.repository.GroupRightRepository;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GroupService {

    private static final GroupDTOBuilder GROUP_DTO_BUILDER = new GroupDTOBuilder();

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final GroupRightRepository groupRightRepository;

    private final AuthenticationFacade authenticationFacade;

    public GroupService(
            AuthenticationFacade authenticationFacade,
            GroupRepository groupRepository,
            UserRepository userRepository,
            GroupRightRepository groupRightRepository) {
        this.authenticationFacade = authenticationFacade;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupRightRepository = groupRightRepository;
    }

    public GroupDTO addUserToGroup(Integer userGroupId, AddUserGroupRequest addUserGroupRequest) {
        Group group = this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));

        final User currentUser = this.authenticationFacade.getCurrentUser();

        group.checkIsAdmin(currentUser);

        final User userToAdd = this.userRepository
                .findById(addUserGroupRequest.getUserId())
                .orElseThrow(() -> new UserNotExistException(addUserGroupRequest.getUserId()));

        final GroupRightEnum rightEnum =
                GroupRightEnum.valueOf(addUserGroupRequest.getAuthorization().getValue());

        if (group.hasAnyRight(userToAdd)) {
            throw new UserAlreadyAccessGroupException(userToAdd, group);
        }

        GroupRight groupRight = GroupRight.builder()
                .groupRightEnum(rightEnum)
                .user(userToAdd)
                .group(group)
                .build();

        groupRight = this.groupRightRepository.save(groupRight);

        group.addGroupRight(groupRight);
        userToAdd.addGroupRight(groupRight);

        return GROUP_DTO_BUILDER.transform(group);
    }

    public GroupDTO createGroup(GroupCreationRequest groupCreationRequest) {
        Group group =
                Group.builder().name(groupCreationRequest.getGroupName()).build();

        group = this.groupRepository.save(group);

        final User user = this.authenticationFacade.getCurrentUser();

        GroupRight groupRight = GroupRight.builder()
                .group(group)
                .user(user)
                .groupRightEnum(GroupRightEnum.ADMIN)
                .build();

        groupRight = this.groupRightRepository.save(groupRight);

        group.addGroupRight(groupRight);
        user.addGroupRight(groupRight);

        return GROUP_DTO_BUILDER.transform(group);
    }

    public GroupDTO getGroup(Integer userGroupId) {
        final Group group =
                this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));
        final User user = this.authenticationFacade.getCurrentUser();
        group.checkCanRead(user);
        return GROUP_DTO_BUILDER.transform(group);
    }

    public List<GroupDTO> getGroups() {
        final User user = this.authenticationFacade.getCurrentUser();
        final var groups =
                user.getGroupRights().stream().map(GroupRight::getGroup).toList();
        return GROUP_DTO_BUILDER.transformAll(groups);
    }

    public void removeUserFromGroup(Integer userGroupId, Integer userAccountId) {
        Group group = this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));

        final User user = this.authenticationFacade.getCurrentUser();

        group.checkIsAdmin(user);

        final User userToRemove =
                this.userRepository.findById(userAccountId).orElseThrow(() -> new UserNotExistException(userAccountId));

        final GroupRight groupRightToRemove = group.getGroupRights().stream()
                .filter(groupRight -> groupRight.getUser().equals(userToRemove))
                .findFirst()
                .orElseThrow(() -> new UserNoAccessGroupException(userToRemove, group));

        this.groupRightRepository.delete(groupRightToRemove);
        group.removeGroupRight(groupRightToRemove);
        userToRemove.removeGroupRight(groupRightToRemove);
        this.groupRepository.save(group);
        this.userRepository.save(user);
    }

    public GroupDTO updateGroup(Integer userGroupId, GroupUpdateRequest groupUpdateRequest) {
        Group group = this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));
        final User user = this.authenticationFacade.getCurrentUser();
        group.checkIsAdmin(user);
        group.setName(groupUpdateRequest.getGroupName());
        return GROUP_DTO_BUILDER.transform(group);
    }

    public GroupDTO updateUserInGroup(Integer userGroupId, Integer userAccountId, UpdateUserGroupRequest updateUserGroupRequest) {
        final Group group =
                this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));
        final User user = this.authenticationFacade.getCurrentUser();
        group.checkIsAdmin(user);

        final User userToUpdate =
                this.userRepository.findById(userAccountId).orElseThrow(() -> new UserNotExistException(userAccountId));

        final GroupRight groupRightToUpdate = group.getGroupRights().stream()
                .filter(groupRight -> groupRight.getUser().equals(userToUpdate))
                .findFirst()
                .orElseThrow(() -> new UserNoAccessGroupException(userToUpdate, group));

        final GroupRightEnum rightEnum =
                GroupRightEnum.valueOf(updateUserGroupRequest.getAuthorization().getValue());

        groupRightToUpdate.setGroupRightEnum(rightEnum);

        return GROUP_DTO_BUILDER.transform(group);
    }

    public void deleteGroup(Integer userGroupId) {
        final Group group =
                this.groupRepository.findById(userGroupId).orElseThrow(() -> new GroupNotExistException(userGroupId));

        final User user = this.authenticationFacade.getCurrentUser();

        group.checkIsAdmin(user);

        List<GroupRight> groupRights = this.groupRightRepository.findAllByGroup(group);

        groupRights.forEach(groupRight -> {
            group.removeGroupRight(groupRight);
            groupRight.getUser().removeGroupRight(groupRight);
            this.groupRightRepository.delete(groupRight);
        });

        this.groupRepository.delete(group);
    }
}
