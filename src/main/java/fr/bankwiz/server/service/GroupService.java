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
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.GroupRepository;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GroupService {

    private static final GroupDTOBuilder GROUP_DTO_BUILDER = new GroupDTOBuilder();

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    public GroupService(
            AuthenticationFacade authenticationFacade, GroupRepository groupRepository, UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public GroupDTO addUserToGroup(Integer groupId, AddUserGroupRequest addUserGroupRequest) {
        Group group = this.groupRepository
                .findById(groupId.intValue())
                .orElseThrow(() -> new GroupNotExistException(groupId.intValue()));

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

        final GroupRight groupRight = GroupRight.builder()
                .groupRightEnum(rightEnum)
                .user(userToAdd)
                .group(group)
                .build();

        group.addGroupRight(groupRight);
        userToAdd.addGroupRight(groupRight);

        group = this.groupRepository.save(group);
        this.userRepository.save(userToAdd);

        return GROUP_DTO_BUILDER.transform(group);
    }

    public GroupDTO createGroup(GroupCreationRequest groupCreationRequest) {
        return null;
    }

    public GroupDTO getGroup(Integer groupId) {
        return null;
    }

    public List<GroupDTO> getGroups() {
        return null;
    }

    public GroupDTO removeUserFromGroup(Integer groupId, Integer userId) {
        return null;
    }

    public GroupDTO updateGroup(Integer groupId, GroupUpdateRequest groupUpdateRequest) {
        return null;
    }

    public GroupDTO updateUserInGroup(Integer groupId, Integer userId, UpdateUserGroupRequest updateUserGroupRequest) {
        return null;
    }

    public void deleteGroup(Integer groupId) {}
}
