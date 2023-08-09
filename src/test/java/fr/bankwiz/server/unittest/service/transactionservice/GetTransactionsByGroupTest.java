package fr.bankwiz.server.unittest.service.transactionservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.service.TransactionService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetTransactionsByGroupTest extends UnitTestBase {

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
    void getTransactionByGroupOkAdmin() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        List<Transaction> transactions = new ArrayList<>();
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        transactions.add(this.unitTestFactory.getTransaction(bankAccount));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);
        this.groupRepositoryMockFactory.mockFindById(group.getUserGroupId(), group);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccountIn(List.of(bankAccount), transactions);

        var result = this.transactionService.getTransactionsByGroup(group.getUserGroupId());

        Assertions.assertAll(() -> Assertions.assertEquals(1, result.size()), () -> {
            for (TransactionDTO transactionDTO : result) {
                Assertions.assertTrue(group.getBankAccounts().stream()
                        .map(BankAccount::getId)
                        .anyMatch(id ->
                                id.equals(transactionDTO.getAccountIndexDTO().getAccountId())));
            }
        });
    }

    @Test
    void getTransactionByGroupOkWrite() throws Exception {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        List<Transaction> transactions = new ArrayList<>();
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        transactions.add(this.unitTestFactory.getTransaction(bankAccount));
        final BankAccount bankAccount2 = this.unitTestFactory.getBankAccount(group);
        transactions.add(this.unitTestFactory.getTransaction(bankAccount2));
        transactions.add(this.unitTestFactory.getTransaction(bankAccount2));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount2.getId(), bankAccount2);
        this.groupRepositoryMockFactory.mockFindById(group.getUserGroupId(), group);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccountIn(
                List.of(bankAccount, bankAccount2), transactions);

        var result = this.transactionService.getTransactionsByGroup(group.getUserGroupId());

        Assertions.assertAll(() -> Assertions.assertEquals(3, result.size()), () -> {
            for (TransactionDTO transactionDTO : result) {
                Assertions.assertTrue(group.getBankAccounts().stream()
                        .map(BankAccount::getId)
                        .anyMatch(id ->
                                id.equals(transactionDTO.getAccountIndexDTO().getAccountId())));
            }
        });
    }

    @Test
    void getTransactionByGroupOkRead() throws Exception {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        List<Transaction> transactions = new ArrayList<>();
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);
        transactions.add(unitTestFactory.getTransaction(bankAccount));
        final BankAccount bankAccount2 = this.unitTestFactory.getBankAccount(group);
        transactions.add(this.unitTestFactory.getTransaction(bankAccount2));
        transactions.add(this.unitTestFactory.getTransaction(bankAccount2));
        final Group group2 = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount3 = this.unitTestFactory.getBankAccount(group2);
        this.unitTestFactory.getTransaction(bankAccount3);
        this.unitTestFactory.getTransaction(bankAccount3);
        this.unitTestFactory.getTransaction(bankAccount3);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount2.getId(), bankAccount2);
        this.groupRepositoryMockFactory.mockFindById(group.getUserGroupId(), group);
        this.transactionRepositoryMockFactory.mockFindAllByBankAccountIn(
                List.of(bankAccount, bankAccount2), transactions);

        var result = this.transactionService.getTransactionsByGroup(group.getUserGroupId());

        Assertions.assertAll(() -> Assertions.assertEquals(3, result.size()), () -> {
            for (TransactionDTO transactionDTO : result) {
                Assertions.assertTrue(group.getBankAccounts().stream()
                        .map(BankAccount::getId)
                        .anyMatch(id ->
                                id.equals(transactionDTO.getAccountIndexDTO().getAccountId())));
            }
        });
    }

    @Test
    void userNoWritePermissionException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccount.getGroup().getUserGroupId(), bankAccount.getGroup());
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccount.getId(), bankAccount);

        Integer userGroupId = group.getUserGroupId();

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.transactionService.getTransactionsByGroup(userGroupId);
        });
    }

    @Test
    void groupNotExistException() {
        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.transactionService.getTransactionsByGroup(groupId);
        });
    }
}
