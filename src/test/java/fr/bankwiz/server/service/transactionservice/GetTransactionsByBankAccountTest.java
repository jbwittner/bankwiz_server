package fr.bankwiz.server.service.transactionservice;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class GetTransactionsByBankAccountTest extends UnitTestBase {

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
    void getTransactionByBankAccountOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        List<Transaction> transactions = List.of(this.unitTestFactory.getTransaction(bankAccount));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccount(bankAccount, transactions);

        var result = this.transactionService.getTransactionsByBankAccount(bankAccount.getId());

        Assertions.assertAll(() -> Assertions.assertEquals(1, result.size()), () -> {
            for (TransactionDTO transactionDTO : result) {
                Assertions.assertEquals(
                        bankAccount.getId(), transactionDTO.getAccountIndexDTO().getAccountId());
            }
        });
    }

    @Test
    void getTransactionByBankAccountOkWrite() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final Group group2 = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        this.unitTestFactory.getTransaction(bankAccount);
        final BankAccount bankAccount2 = this.unitTestFactory.getBankAccount(group2);
        List<Transaction> transactions = List.of(
                this.unitTestFactory.getTransaction(bankAccount2), this.unitTestFactory.getTransaction(bankAccount2));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount2.getId(), bankAccount2);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccount(bankAccount2, transactions);

        var result = this.transactionService.getTransactionsByBankAccount(bankAccount2.getId());

        Assertions.assertAll(() -> Assertions.assertEquals(2, result.size()), () -> {
            for (TransactionDTO transactionDTO : result) {
                Assertions.assertEquals(
                        bankAccount2.getId(),
                        transactionDTO.getAccountIndexDTO().getAccountId());
            }
        });
    }

    @Test
    void getTransactionByBankAccountOkRead() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        this.unitTestFactory.getTransaction(bankAccount);
        final BankAccount bankAccount2 = this.unitTestFactory.getBankAccount(group);
        this.unitTestFactory.getTransaction(bankAccount2);
        this.unitTestFactory.getTransaction(bankAccount2);
        final Group group2 = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount3 = this.unitTestFactory.getBankAccount(group2);
        List<Transaction> transactions = List.of();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount3.getId(), bankAccount3);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccount(bankAccount3, transactions);

        var result = this.transactionService.getTransactionsByBankAccount(bankAccount3.getId());

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void userNoReadPermissionException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        this.unitTestFactory.getTransaction(bankAccount);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), bankAccount.getGroup());
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);

        Integer id = bankAccount.getId();

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.transactionService.getTransactionsByBankAccount(id);
        });
    }

    @Test
    void bankAccountNotExistException() {
        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, Optional.empty());

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.transactionService.getTransactionsByBankAccount(bankAccountId);
        });
    }
}
