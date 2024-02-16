package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.exception.UserNotExistException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.input.AddUserGroupInputDomain;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class AddUserToGroupTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
        this.groupDomainService = new GroupDomainService(
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockUserSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    @Test
    void addUserOk() {
        final UserDomain admin = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final UserDomain userToAdd = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToAdd.getId(), Optional.of(userToAdd));

        final AddUserGroupInputDomain addUserGroupInput = AddUserGroupInputDomain.builder()
                .userId(userToAdd.getId())
                .right(GroupRightEnum.READ)
                .build();

        final GroupRightDomain groupRight = this.groupDomainService.addUserToGroup(group.getId(), addUserGroupInput);

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, groupRight.getGroup()),
                () -> Assertions.assertEquals(userToAdd, groupRight.getUser()),
                () -> Assertions.assertEquals(GroupRightEnum.READ, groupRight.getGroupRightEnum()));
    }

    @Test
    void userHaveAlreadyAccess() {
        final UserDomain admin = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final UserDomain userToAdd = this.factory.getUser();
        groupRights.add(this.factory.getGroupRight(group, userToAdd, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToAdd.getId(), Optional.of(userToAdd));

        final AddUserGroupInputDomain addUserGroupInput = AddUserGroupInputDomain.builder()
                .userId(userToAdd.getId())
                .right(GroupRightEnum.WRITE)
                .build();

        final UUID groupId = group.getId();

        Assertions.assertThrows(UserAlreadyAccessGroupException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, addUserGroupInput);
        });
    }

    @Test
    void userNotAdmin() {
        final UserDomain admin = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.WRITE));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final UUID groupId = group.getId();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, null);
        });
    }

    @Test
    void userNotExist() {
        final UserDomain admin = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();

        final AddUserGroupInputDomain addUserGroupInput = AddUserGroupInputDomain.builder()
                .userId(UUID.randomUUID())
                .right(GroupRightEnum.WRITE)
                .build();

        final UUID groupId = group.getId();

        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, addUserGroupInput);
        });
    }

    @Test
    void groupNotExist() {
        final UserDomain admin = this.factory.getUser();

        final UUID groupId = UUID.randomUUID();

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(groupId, Optional.empty());

        final AddUserGroupInputDomain addUserGroupInput = AddUserGroupInputDomain.builder()
                .userId(UUID.randomUUID())
                .right(GroupRightEnum.WRITE)
                .build();

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, addUserGroupInput);
        });
    }
}
