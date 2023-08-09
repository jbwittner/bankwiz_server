package fr.bankwiz.server.service.transactionservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class DeleteTransactionTest extends UnitTestBase {

    private TransactionService transactionService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionService = new TransactionService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.bankAccountRepositoryMockFactory.getRepository(),
                this.groupRepositoryMockFactory.getRepository(),
                this.transactionRepositoryMockFactory.getRepository());
    }

    @Test
    void deleteTransactionOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockDelete(transaction);

        this.transactionService.deleteTransaction(transactionId);

        this.transactionRepositoryMockFactory.verifyDeleteCalled(transaction);
    }

    @Test
    void deleteTransactionOkWrite() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockDelete(transaction);

        this.transactionService.deleteTransaction(transactionId);

        this.transactionRepositoryMockFactory.verifyDeleteCalled(transaction);
    }

    @Test
    void userNoWritePermissionException() throws Exception {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockDelete(transaction);

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            this.transactionService.deleteTransaction(transactionId);
        });
    }

    @Test
    void transactionNotExistException() {
        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.transactionRepositoryMockFactory.mockFindById(transactionId, Optional.empty());

        Assertions.assertThrows(TransactionNotExistException.class, () -> {
            this.transactionService.deleteTransaction(transactionId);
        });
    }
}
