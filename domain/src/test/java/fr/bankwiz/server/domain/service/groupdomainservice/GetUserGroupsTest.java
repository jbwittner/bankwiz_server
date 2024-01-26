package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class GetUserGroupsTest extends DomainUnitTestBase {

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
    void getGroupOk() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final List<GroupRight> groupRights = new ArrayList<>();
        final GroupRight groupRight1 = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);
        final GroupRight groupRight2 = this.factory.getGroupRight(user, GroupRightEnum.READ);

        groupRights.add(groupRight1);
        groupRights.add(groupRight2);

        this.mockGroupRightSpi.mockFindByUser(user, groupRights);

        final List<Group> groups = this.groupDomainService.getUserGroups();

        Assertions.assertEquals(groupRights.size(), groups.size());

        Assertions.assertEquals(groupRight1.getGroup(), groups.get(0));
        Assertions.assertEquals(groupRight2.getGroup(), groups.get(1));
    }
}
