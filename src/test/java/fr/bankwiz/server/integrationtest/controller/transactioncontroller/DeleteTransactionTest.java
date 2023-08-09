package fr.bankwiz.server.integrationtest.controller.transactioncontroller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.*;

class DeleteTransactionTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void deleteTransactionOkAdmin() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        this.client
                .doDelete(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final Optional<Transaction> optionalTransaction = this.transactionRepository.findById(transactionId);

        Assertions.assertFalse(optionalTransaction.isPresent());
    }

    @Test
    void deleteTransactionOkWrite() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        this.client
                .doDelete(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final Optional<Transaction> optionalTransaction = this.transactionRepository.findById(transactionId);

        Assertions.assertFalse(optionalTransaction.isPresent());
    }

    @Test
    void userNoWritePermissionException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        final Transaction transaction = this.integrationTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final Optional<Transaction> optionalTransaction = this.transactionRepository.findById(transactionId);

        Assertions.assertTrue(optionalTransaction.isPresent());

        final UserNoWriteRightException exception = new UserNoWriteRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void transactionNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_ID.getUri(transactionId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final TransactionNotExistException exception = new TransactionNotExistException(transactionId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
