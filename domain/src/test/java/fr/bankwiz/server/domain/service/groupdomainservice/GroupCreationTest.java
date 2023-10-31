package fr.bankwiz.server.domain.service.groupdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.request.GroupCreationRequest;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class GroupCreationTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.groupDomainService = new GroupDomainService(
                this.mockGroupSpi.getMock(), this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @Test
    void creationOk() {
        final GroupCreationRequest groupCreationRequest = GroupCreationRequest.builder()
                .groupName(this.faker.space().star())
                .build();

        this.mockGroupRightSpi.mockSave();
        this.mockGroupSpi.mockSave();

        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final Group group = this.groupDomainService.groupCreation(groupCreationRequest);

        Assertions.assertEquals(groupCreationRequest.getGroupName(), group.getGroupName());
        Assertions.assertNotNull(group.getGroupUuid());

        final var groupeRightsSaved = this.mockGroupRightSpi.getGroupRightsSaved();

        Assertions.assertEquals(1, groupeRightsSaved.size());

        final GroupRight groupRight = groupeRightsSaved.get(0);

        Assertions.assertNotNull(groupRight.getGroupRightUuid());

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, groupRight.getGroup()),
                () -> Assertions.assertEquals(user, groupRight.getUser()),
                () -> Assertions.assertEquals(GroupRightEnum.ADMIN, groupRight.getGroupRightEnum()));
    }
}
