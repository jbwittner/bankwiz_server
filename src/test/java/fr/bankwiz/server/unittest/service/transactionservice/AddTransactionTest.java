package fr.bankwiz.server.unittest.service.transactionservice;

import fr.bankwiz.openapi.model.TransactionCreationRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Optional;

class AddTransactionTest extends UnitTestBase {

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
    void addTransactionOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), group);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);
        this.transactionRepositoryMockFactory.mockSave();

        final TransactionDTO transactionDTO =
                this.transactionService.addTransaction(transactionCreationRequest);

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

        var argumentCaptor = this.transactionRepositoryMockFactory.verifySaveCalled(Transaction.class);
        final Transaction transactionSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionCreationRequest.getAccountId(), transactionSaved.getBankAccount().getId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getDate(),
                        transactionSaved.getDate()));
    }

    @Test
    void addTransactionOkWrite() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), group);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);
        this.transactionRepositoryMockFactory.mockSave();

        final TransactionDTO transactionDTO =
                this.transactionService.addTransaction(transactionCreationRequest);

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

        var argumentCaptor = this.transactionRepositoryMockFactory.verifySaveCalled(Transaction.class);
        final Transaction transactionSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionCreationRequest.getAccountId(), transactionSaved.getBankAccount().getId()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getAmountInCents(), transactionSaved.getAmount()),
                () -> Assertions.assertEquals(
                        transactionCreationRequest.getDate(),
                        transactionSaved.getDate()));
    }

    @Test
    void userNoWritePermissionException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), group);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccount.getId(),
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), bankAccount.getGroup());

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            this.transactionService.addTransaction(transactionCreationRequest);
        });
    }

    @Test
    void bankAccountNotExistException() {
        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        TransactionCreationRequest transactionCreationRequest = new TransactionCreationRequest(
                bankAccountId,
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, Optional.empty());

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.transactionService.addTransaction(transactionCreationRequest);
        });
    }
}
