package fr.bankwiz.server.integrationtest.controller.transactioncontroller;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionUpdateRequest;
import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZoneId;

class UpdateTransactionTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void updateTransactionOkAdmin() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doPut(uri, user.getAuthId(), transactionUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));

        final Transaction transactionSaved =
                this.transactionRepository.findById(transactionDTO.getTransactionIndexDTO().getTransactionId()).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionSaved.getDate())
        );
    }

    @Test
    void updateTransactionOkWrite() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doPut(uri, user.getAuthId(), transactionUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final TransactionDTO transactionDTO =
                IntegrationMVCClient.convertMvcResultToResponseObject(result, TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(),
                        transactionDTO.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionDTO.getTransactionIndexDTO().getDate()));

        final Transaction transactionSaved =
                this.transactionRepository.findById(transactionDTO.getTransactionIndexDTO().getTransactionId()).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionSaved.getDate())
        );
    }

    @Test
    void userNoWritePermissionException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        var result = this.client
                .doPut(uri, user.getAuthId(), transactionUpdateRequest);

        final Transaction transactionSaved =
                this.transactionRepository.findById(transactionId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(
                        transactionUpdateRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertNotEquals(
                        transactionUpdateRequest.getDate(),
                        transactionSaved.getDate())
        );

        final UserNoWriteRightException exception = new UserNoWriteRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void transactionNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        final var result = this.client.doPut(uri, user.getAuthId(), transactionUpdateRequest);

        final TransactionNotExistException exception = new TransactionNotExistException(transactionId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
