package fr.bankwiz.server.integrationtest.controller.bankaccountcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class GetBankAccountTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getAccountTest() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final BankAccountDTO bankAccountDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, BankAccountDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccount.getId(), bankAccountDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        bankAccount.getName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccount.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        group.getUserGroupId(),
                        bankAccountDTO.getGroupIndexDTO().getGroupId()));
    }

    @Test
    void userNoReadRightException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroup();
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doGet(uri, user.getAuthId());

        final UserNoReadRightException exception = new UserNoReadRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void bankAccountNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doGet(uri, user.getAuthId());

        final BankAccountNotExistException exception = new BankAccountNotExistException(bankAccountId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
