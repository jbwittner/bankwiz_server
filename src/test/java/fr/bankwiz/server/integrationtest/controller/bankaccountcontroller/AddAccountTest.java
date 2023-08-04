package fr.bankwiz.server.integrationtest.controller.bankaccountcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class AddAccountTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void addAccountOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(
                group.getUserGroupId(),
                this.faker.witcher().character(),
                this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT.getUri();

        var result = this.client
                .doPost(uri, user.getAuthId(), bankAccountCreationRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final BankAccountDTO bankAccountDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, BankAccountDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getAccountName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getGroupId(),
                        bankAccountDTO.getGroupIndexDTO().getGroupId()));

        final BankAccount bankAccountSaved =
                this.bankAccountRepository.findById(1).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountCreationRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getGroupId(),
                        bankAccountSaved.getGroup().getUserGroupId()));
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(
                group.getUserGroupId(),
                this.faker.witcher().character(),
                this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT.getUri();

        final var result = this.client.doPost(uri, user.getAuthId(), bankAccountCreationRequest);

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(
                groupId, this.faker.witcher().character(), this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT.getUri();

        final var result = this.client.doPost(uri, user.getAuthId(), bankAccountCreationRequest);

        final GroupNotExistException exception = new GroupNotExistException(groupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
