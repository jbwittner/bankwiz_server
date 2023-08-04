package fr.bankwiz.server.unittest.service.bankaccountservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.BankAccountService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class DeleteAccountTest extends UnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountService = new BankAccountService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    void deleteOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory
                .mockFindById(bankAccountId, bankAccount)
                .mockDelete(bankAccount);

        this.bankAccountService.deleteAccount(bankAccountId);

        this.bankAccountRepositoryMockFactory.verifyDeleteCalled(bankAccount);

        Assertions.assertEquals(0, group.getBankAccounts().size());
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, bankAccount);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.bankAccountService.deleteAccount(bankAccountId);
        });
    }

    @Test
    void bankAccountNotExistException() {

        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, Optional.empty());

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.bankAccountService.deleteAccount(bankAccountId);
        });
    }
}
