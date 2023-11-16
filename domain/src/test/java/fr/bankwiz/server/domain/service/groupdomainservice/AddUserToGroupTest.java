package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.AddUserGroupInput;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class AddUserToGroupTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools = new CheckRightTools(this.mockGroupRightSpi.getMock());
        this.groupDomainService = new GroupDomainService(
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockUserSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    @Test
    void addUserOk() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final User userToAdd = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getGroupId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToAdd.getUserId(), Optional.of(userToAdd));

        final AddUserGroupInput addUserGroupInput = AddUserGroupInput.builder()
                .userId(userToAdd.getUserId())
                .right(GroupRightEnum.READ)
                .build();

        final GroupRight groupRight = this.groupDomainService.addUserToGroup(group.getGroupId(), addUserGroupInput);

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, groupRight.getGroup()),
                () -> Assertions.assertEquals(userToAdd, groupRight.getUser()),
                () -> Assertions.assertEquals(GroupRightEnum.READ, groupRight.getGroupRightEnum()));
    }

    @Test
    void userHaveAlreadyAccess() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));

        final User userToAdd = this.factory.getUser();
        groupRights.add(this.factory.getGroupRight(group, userToAdd, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getGroupId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights).mockSave();
        this.mockUserSpi.mockFindById(userToAdd.getUserId(), Optional.of(userToAdd));

        final AddUserGroupInput addUserGroupInput = AddUserGroupInput.builder()
                .userId(userToAdd.getUserId())
                .right(GroupRightEnum.WRITE)
                .build();

        final UUID groupId = group.getGroupId();

        Assertions.assertThrows(UserAlreadyAccessGroupException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, addUserGroupInput);
        });
    }

    @Test
    void userNotAdmin() {
        final User admin = this.factory.getUser();
        final Group group = this.factory.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.WRITE));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(group.getGroupId(), Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final UUID groupId = group.getGroupId();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupDomainService.addUserToGroup(groupId, null);
        });
    }
}
