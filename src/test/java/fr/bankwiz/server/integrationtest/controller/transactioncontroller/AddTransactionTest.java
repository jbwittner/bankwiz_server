package fr.bankwiz.server.integrationtest.controller.transactioncontroller;

import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.TransactionCreationRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.*;

class AddTransactionTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void addTransactionOkAdmin() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date()
                        .birthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION.getUri();

        var result = this.client
                .doPost(uri, user.getAuthId(), transactionCreationRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAccountId(),
                        transactionDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));

        final Transaction transactionSaved = this.transactionRepository
                .findById(transactionDTO.getTransactionIndexDTO().getTransactionId())
                .orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAccountId(),
                        transactionSaved.getBankAccount().getId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(transactionCreationRequest.getDate(), transactionSaved.getDate()));
    }

    @Test
    void addTransactionOkWrite() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date()
                        .birthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION.getUri();

        var result = this.client
                .doPost(uri, user.getAuthId(), transactionCreationRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAccountId(),
                        transactionDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));

        final Transaction transactionSaved = this.transactionRepository
                .findById(transactionDTO.getTransactionIndexDTO().getTransactionId())
                .orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAccountId(),
                        transactionSaved.getBankAccount().getId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(transactionCreationRequest.getDate(), transactionSaved.getDate()));
    }

    @Test
    void userNoWritePermissionException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date()
                        .birthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION.getUri();

        final var result = this.client.doPost(uri, user.getAuthId(), transactionCreationRequest);

        final UserNoWriteRightException exception = new UserNoWriteRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void bankAccountNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccountId,
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date()
                        .birthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION.getUri();

        final var result = this.client.doPost(uri, user.getAuthId(), transactionCreationRequest);

        final BankAccountNotExistException exception = new BankAccountNotExistException(bankAccountId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
