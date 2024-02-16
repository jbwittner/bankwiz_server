package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.exception.GroupDeletionWithBankAccountsException;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.domain.exception.UserNoAccessGroupException;
import fr.bankwiz.server.domain.exception.UserNotExistException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.input.AddUserGroupInputDomain;
import fr.bankwiz.server.domain.model.input.GroupCreationInputDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class GroupDomainService implements GroupApi {

    private final GroupSpi groupSpi;
    private final GroupRightSpi groupRightSpi;
    private final UserSpi userSpi;
    private final BankAccountSpi bankAccountSpi;
    private final AuthenticationSpi authenticationSpi;
    private final CheckRightTools checkRightTools;

    public GroupDomainService(
            GroupSpi groupSpi,
            GroupRightSpi groupRightSpi,
            UserSpi userSpi,
            BankAccountSpi bankAccountSpi,
            AuthenticationSpi authenticationSpi,
            CheckRightTools checkRightTools) {
        this.groupSpi = groupSpi;
        this.groupRightSpi = groupRightSpi;
        this.userSpi = userSpi;
        this.bankAccountSpi = bankAccountSpi;
        this.authenticationSpi = authenticationSpi;
        this.checkRightTools = checkRightTools;
    }

    @Override
    public GroupDomain groupCreation(GroupCreationInputDomain groupCreationInput) {
        final GroupDomain group = GroupDomain.builder()
                .id(UUID.randomUUID())
                .groupName(groupCreationInput.getGroupName())
                .build();
        final GroupDomain groupSaved = this.groupSpi.save(group);
        final UserDomain admin = this.authenticationSpi.getCurrentUser();
        final GroupRightDomain groupRight = GroupRightDomain.builder()
                .group(groupSaved)
                .user(admin)
                .id(UUID.randomUUID())
                .groupRightEnum(GroupRightEnum.ADMIN)
                .build();
        this.groupRightSpi.save(groupRight);
        return groupSaved;
    }

    @Override
    public List<GroupDomain> getUserGroups() {
        final UserDomain user = this.authenticationSpi.getCurrentUser();
        final List<GroupRightDomain> groupRights = this.groupRightSpi.findByUser(user);
        return groupRights.stream().map(GroupRightDomain::getGroup).toList();
    }

    @Override
    public GroupDetailsDomain getGroupDetails(UUID groupId) {
        final Optional<GroupDomain> optionalGroup = this.groupSpi.findById(groupId);
        final GroupDomain group = optionalGroup.orElseThrow(() -> new GroupNotExistException(groupId));
        this.checkRightTools.checkCanRead(this.authenticationSpi.getCurrentUser(), group);
        final List<GroupRightDomain> groupRights = this.groupRightSpi.findByGroup(group);
        return GroupDetailsDomain.builder()
                .group(group)
                .groupRights(groupRights)
                .build();
    }

    @Override
    public GroupRightDomain addUserToGroup(UUID groupId, AddUserGroupInputDomain addUserGroupInput) {
        final GroupDomain group =
                this.groupSpi.findById(groupId).orElseThrow(() -> new GroupNotExistException(groupId));

        this.checkRightTools.checkIsAdmin(this.authenticationSpi.getCurrentUser(), group);

        final UserDomain userToAdd = this.userSpi
                .findById(addUserGroupInput.getUserId())
                .orElseThrow(() -> new UserNotExistException(addUserGroupInput.getUserId()));

        if (this.checkRightTools.hasAnyRight(userToAdd, group)) {
            throw new UserAlreadyAccessGroupException(userToAdd, group);
        }

        final GroupRightDomain groupRight = GroupRightDomain.builder()
                .group(group)
                .user(userToAdd)
                .id(UUID.randomUUID())
                .groupRightEnum(addUserGroupInput.getRight())
                .build();
        return this.groupRightSpi.save(groupRight);
    }

    @Override
    public void deleteUserFromGroup(UUID groupId, UUID userId) {
        final GroupDomain group =
                this.groupSpi.findById(groupId).orElseThrow(() -> new GroupNotExistException(groupId));

        this.checkRightTools.checkIsAdmin(this.authenticationSpi.getCurrentUser(), group);

        final UserDomain userToRemove =
                this.userSpi.findById(userId).orElseThrow(() -> new UserNotExistException(userId));

        if (!this.checkRightTools.hasAnyRight(userToRemove, group)) {
            throw new UserNoAccessGroupException(userToRemove, group);
        }

        this.groupRightSpi.deleteByGroupAndUser(group, userToRemove);
    }

    @Override
    public void deleteGroup(UUID groupId) {
        final GroupDomain group =
                this.groupSpi.findById(groupId).orElseThrow(() -> new GroupNotExistException(groupId));
        this.checkRightTools.checkIsAdmin(this.authenticationSpi.getCurrentUser(), group);

        if (this.bankAccountSpi.existsByGroup(group)) {
            throw new GroupDeletionWithBankAccountsException(groupId);
        }

        this.groupRightSpi.deleteAllByGroup(group);
        this.groupSpi.deleteById(groupId);
    }
}
