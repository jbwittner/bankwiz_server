package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserNoAccessGroupException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.exception.UserNotExistException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class DeleteUserFromGroupTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.groupDomainService = this.buildGroupDomainService();
    }

    @Test
    void deleteUserOk() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final User userToRemove = this.factory.getUser();
        groupRights.add(this.factory.getGroupRight(group, userToRemove, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToRemove.getId(), Optional.of(userToRemove));

        this.groupDomainService.deleteUserFromGroup(group.getId(), userToRemove.getId());

        Mockito.verify(this.mockGroupRightSpi.getMock(), Mockito.times(1)).deleteByGroupAndUser(group, userToRemove);
    }

    @Test
    void userHaveNotAccess() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final User userToRemove = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToRemove.getId(), Optional.of(userToRemove));

        final UUID groupId = group.getId();
        final UUID userToRemoveId = userToRemove.getId();

        Assertions.assertThrows(UserNoAccessGroupException.class, () -> {
            this.groupDomainService.deleteUserFromGroup(groupId, userToRemoveId);
        });

        Mockito.verify(this.mockGroupRightSpi.getMock(), Mockito.never()).deleteByGroupAndUser(group, userToRemove);
    }

    @Test
    void userToRemoveNotExist() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();

        final UUID userToRemoveId = UUID.randomUUID();

        this.mockUserSpi.mockFindById(userToRemoveId, Optional.empty());

        final UUID groupId = group.getId();

        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.groupDomainService.deleteUserFromGroup(groupId, userToRemoveId);
        });
    }

    @Test
    void userNotAdmin() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.WRITE));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();

        final UUID userToRemoveId = UUID.randomUUID();
        final UUID groupId = group.getId();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupDomainService.deleteUserFromGroup(groupId, userToRemoveId);
        });
    }

    @Test
    void GroupNotExist() {
        final UUID groupId = UUID.randomUUID();
        this.mockGroupSpi.mockFindById(groupId, Optional.empty());

        final UUID userToRemoveId = UUID.randomUUID();

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupDomainService.deleteUserFromGroup(groupId, userToRemoveId);
        });
    }
}
