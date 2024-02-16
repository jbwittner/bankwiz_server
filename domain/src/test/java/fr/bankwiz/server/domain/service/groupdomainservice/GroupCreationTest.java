package fr.bankwiz.server.domain.service.groupdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class GroupCreationTest extends DomainUnitTestBase {

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
    void creationOk() {
        final GroupCreationInput groupCreationRequest = GroupCreationInput.builder()
                .groupName(this.faker.space().star())
                .build();

        this.mockGroupRightSpi.mockSave();
        this.mockGroupSpi.mockSave();

        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final GroupDomain group = this.groupDomainService.groupCreation(groupCreationRequest);

        Assertions.assertEquals(groupCreationRequest.getGroupName(), group.getGroupName());
        Assertions.assertNotNull(group.getId());

        final var groupeRightsSaved = this.mockGroupRightSpi.getGroupRightsSaved();

        Assertions.assertEquals(1, groupeRightsSaved.size());

        final GroupRightDomain groupRight = groupeRightsSaved.get(0);

        Assertions.assertNotNull(groupRight.getId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, groupRight.getGroup()),
                () -> Assertions.assertEquals(user, groupRight.getUser()),
                () -> Assertions.assertEquals(GroupRightEnum.ADMIN, groupRight.getGroupRightEnum()));
    }
}
