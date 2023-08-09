package fr.bankwiz.server.unittest.service.transactionservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetTransactionTest extends UnitTestBase {

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
    void getTransactionOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory.mockFindById(transactionId, transaction);

        var result = this.transactionService.getTransaction(transactionId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        result.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        result.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        result.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(), result.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(), result.getTransactionIndexDTO().getDate()));
    }

    @Test
    void getTransactionOkWrite() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory.mockFindById(transactionId, transaction);

        var result = this.transactionService.getTransaction(transactionId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        result.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        result.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        result.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(), result.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(), result.getTransactionIndexDTO().getDate()));
    }

    @Test
    void getTransactionOkRead() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory.mockFindById(transactionId, transaction);

        var result = this.transactionService.getTransaction(transactionId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        result.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getName(),
                        result.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getBaseAmountDecimal(),
                        result.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        transaction.getAmount(), result.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transaction.getDate(), result.getTransactionIndexDTO().getDate()));
    }

    @Test
    void userNoReadPermissionException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory.mockFindById(transactionId, transaction);

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.transactionService.getTransaction(transactionId);
        });
    }

    @Test
    void transactionNotExistException() {
        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        Assertions.assertThrows(TransactionNotExistException.class, () -> {
            this.transactionService.getTransaction(transactionId);
        });
    }
}
