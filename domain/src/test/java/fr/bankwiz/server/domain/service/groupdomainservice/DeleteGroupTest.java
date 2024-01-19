package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.exception.GroupDeletionWithBankAccountsException;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class DeleteGroupTest extends DomainUnitTestBase {

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
    void deleteGroupOk() {
        final User admin = this.factory.getUser();

        final User otherUser = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final UUID groupId = group.getId();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));
        groupRights.add(this.factory.getGroupRight(group, otherUser, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(groupId, Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        this.groupDomainService.deleteGroup(groupId);

        Mockito.verify(this.mockGroupSpi.getMock(), Mockito.times(1)).deleteById(groupId);
        Mockito.verify(this.mockGroupRightSpi.getMock(), Mockito.times(1)).deleteAllByGroup(group);
    }

    @Test
    void groupHaveBankAccounts() {
        final User admin = this.factory.getUser();

        final User otherUser = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final UUID groupId = group.getId();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, admin, GroupRightEnum.ADMIN));
        groupRights.add(this.factory.getGroupRight(group, otherUser, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(admin);
        this.mockGroupSpi.mockFindById(groupId, Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);
        this.mockBankAccountSpi.mockExistsByGroup(group, true);

        Assertions.assertThrows(GroupDeletionWithBankAccountsException.class, () -> {
            this.groupDomainService.deleteGroup(groupId);
        });
    }

    @Test
    void userNotAdmin() {
        final User notAdmin = this.factory.getUser();

        final User otherUser = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final UUID groupId = group.getId();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, notAdmin, GroupRightEnum.WRITE));
        groupRights.add(this.factory.getGroupRight(group, otherUser, GroupRightEnum.READ));

        this.mockAuthenticationSpi.mockGetCurrentUser(notAdmin);
        this.mockGroupSpi.mockFindById(groupId, Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupDomainService.deleteGroup(groupId);
        });
    }

    @Test
    void groupNotExist() {
        final User notAdmin = this.factory.getUser();
        final UUID groupId = UUID.randomUUID();

        this.mockAuthenticationSpi.mockGetCurrentUser(notAdmin);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupDomainService.deleteGroup(groupId);
        });
    }
}
