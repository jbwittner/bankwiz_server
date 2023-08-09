package fr.bankwiz.server.integrationtest.controller.transactioncontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.*;

class GetTransactionTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getTransactionOkAdmin() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        transactionDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        transactionDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        transactionDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));
    }

    @Test
    void getTransactionOkWrite() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        transactionDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        transactionDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        transactionDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));
    }

    @Test
    void getTransactionOkRead() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        transactionDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        transactionDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        transactionDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));
    }

    @Test
    void userNoReadPermissionException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroup();

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        final var result = this.client.doGet(uri, user.getAuthId());

        final UserNoReadRightException exception = new UserNoReadRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void transactionNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        final var result = this.client.doGet(uri, user.getAuthId());

        final TransactionNotExistException exception = new TransactionNotExistException(transactionId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
