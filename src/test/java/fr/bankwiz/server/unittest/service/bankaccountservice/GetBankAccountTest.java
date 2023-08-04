package fr.bankwiz.server.unittest.service.bankaccountservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.BankAccountService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class GetBankAccountTest extends UnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountService = new BankAccountService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    public void getAccountTest() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, bankAccount);

        final BankAccountDTO bankAccountDTO = this.bankAccountService.getBankAccount(bankAccountId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccount.getId(), bankAccountDTO.getAccountIndexDTO().getAccountId()),
                () -> Assertions.assertEquals(
                        bankAccount.getName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccount.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        group.getUserGroupId(),
                        bankAccountDTO.getGroupIndexDTO().getGroupId()));
    }

    @Test
    public void userNoReadRightException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, bankAccount);

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.bankAccountService.getBankAccount(bankAccountId);
        });
    }

    @Test
    public void bankAccountNotExistException() {
        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, Optional.empty());

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.bankAccountService.getBankAccount(bankAccountId);
        });
    }
}
