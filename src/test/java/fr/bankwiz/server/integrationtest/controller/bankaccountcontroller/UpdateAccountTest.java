package fr.bankwiz.server.integrationtest.controller.bankaccountcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class UpdateAccountTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void updateAllOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setAccountName(this.faker.zelda().character());
        bankAccountUpdateRequest.setBaseAmountDecimal(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        var result = this.client
                .doPut(uri, user.getAuthId(), bankAccountUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final BankAccountDTO bankAccountDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, BankAccountDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getAccountName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertNotEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        final BankAccount bankAccountSaved =
                this.bankAccountRepository.findById(bankAccountId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountUpdateRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertNotEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void updateAccountName() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setAccountName(this.faker.zelda().character());

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        var result = this.client
                .doPut(uri, user.getAuthId(), bankAccountUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final BankAccountDTO bankAccountDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, BankAccountDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getAccountName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        final BankAccount bankAccountSaved =
                this.bankAccountRepository.findById(bankAccountId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountUpdateRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void updateBaseAmount() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setBaseAmountDecimal(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        var result = this.client
                .doPut(uri, user.getAuthId(), bankAccountUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final BankAccountDTO bankAccountDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, BankAccountDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        final BankAccount bankAccountSaved =
                this.bankAccountRepository.findById(bankAccountId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doPut(uri, user.getAuthId(), bankAccountUpdateRequest);

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void bankAccountNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doPut(uri, user.getAuthId(), bankAccountUpdateRequest);

        final BankAccountNotExistException exception = new BankAccountNotExistException(bankAccountId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
