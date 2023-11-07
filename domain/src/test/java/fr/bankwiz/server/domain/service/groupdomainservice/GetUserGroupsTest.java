package fr.bankwiz.server.domain.service.groupdomainservice;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class GetUserGroupsTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.groupDomainService = new GroupDomainService(
                this.mockGroupSpi.getMock(), this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @Test
    void getGroupOk() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final Group group = this.groupDomainService.groupCreation(groupCreationRequest);

        Assertions.assertEquals(groupCreationRequest.getGroupName(), group.getGroupName());
        Assertions.assertNotNull(group.getGroupId());

        final var groupeRightsSaved = this.mockGroupRightSpi.getGroupRightsSaved();

        Assertions.assertEquals(1, groupeRightsSaved.size());

        final GroupRight groupRight = groupeRightsSaved.get(0);

        Assertions.assertNotNull(groupRight.getGroupRightId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, groupRight.getGroup()),
                () -> Assertions.assertEquals(user, groupRight.getUser()),
                () -> Assertions.assertEquals(GroupRightEnum.ADMIN, groupRight.getGroupRightEnum()));
    }
}
