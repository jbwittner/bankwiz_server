package fr.bankwiz.server.unittest.service.transactionservice;

import fr.bankwiz.openapi.model.TransactionUpdateRequest;
import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

class UpdateTransactionTest extends UnitTestBase {

    private TransactionService transactionService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionService = new TransactionService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.bankAccountRepositoryMockFactory.getRepository(),
                this.groupRepositoryMockFactory.getRepository(),
                this.transactionRepositoryMockFactory.getRepository()
        );
    }

    @Test
    void updateTransactionOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockSave();

        var result = this.transactionService.updateTransaction(transactionId, transactionUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(),
                        result.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        result.getTransactionIndexDTO().getDate()));

        var argumentCaptor = this.transactionRepositoryMockFactory.verifySaveCalled(Transaction.class);
        final Transaction transactionSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionSaved.getDate())
        );
    }

    @Test
    void updateTransactionOkWrite() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockSave();

        var result = this.transactionService.updateTransaction(transactionId, transactionUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(),
                        result.getTransactionIndexDTO().getAmountInCents()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        result.getTransactionIndexDTO().getDate()));

        var argumentCaptor = this.transactionRepositoryMockFactory.verifySaveCalled(Transaction.class);
        final Transaction transactionSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionUpdateRequest.getDate(),
                        transactionSaved.getDate())
        );
    }

    @Test
    void userNoWritePermissionException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        final Transaction transaction = this.unitTestFactory.getTransaction(bankAccount);

        final Integer transactionId = transaction.getTransactionId();

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setDate(this.faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        transactionUpdateRequest.setAmountInCents(this.faker.random().nextInt(Integer.MAX_VALUE));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.transactionRepositoryMockFactory
                .mockFindById(transactionId, transaction)
                .mockSave();

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            this.transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        });
    }

    @Test
    void transactionNotExistException() {
        final Integer transactionId = this.faker.random().nextInt(Integer.MAX_VALUE);

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();

        Assertions.assertThrows(TransactionNotExistException.class, () -> {
            this.transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        });
    }
}
