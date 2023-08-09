package fr.bankwiz.server.service.bankaccountservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.BankAccountService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class UpdateAccountTest extends UnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountService = new BankAccountService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    void updateAllOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory
                .mockFindById(bankAccountId, bankAccount)
                .mockSave();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setAccountName(this.faker.zelda().character());
        bankAccountUpdateRequest.setBaseAmountDecimal(this.faker.random().nextInt(Integer.MAX_VALUE));

        final BankAccountDTO bankAccountDTO =
                this.bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getAccountName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertNotEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        var argumentCaptor = this.bankAccountRepositoryMockFactory.verifySaveCalled(BankAccount.class);
        final BankAccount bankAccountSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountUpdateRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertNotEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void updateAccountName() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory
                .mockFindById(bankAccountId, bankAccount)
                .mockSave();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setAccountName(this.faker.zelda().character());

        final BankAccountDTO bankAccountDTO =
                this.bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getAccountName(),
                        bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        var argumentCaptor = this.bankAccountRepositoryMockFactory.verifySaveCalled(BankAccount.class);
        final BankAccount bankAccountSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountUpdateRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void updateBaseAmount() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final String oldAccountName = bankAccount.getName();
        final Integer oldBaseAmountDecimal = bankAccount.getBaseAmountDecimal();

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory
                .mockFindById(bankAccountId, bankAccount)
                .mockSave();

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();
        bankAccountUpdateRequest.setBaseAmountDecimal(this.faker.random().nextInt(Integer.MAX_VALUE));

        final BankAccountDTO bankAccountDTO =
                this.bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(),
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        oldAccountName, bankAccountDTO.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertNotEquals(
                        oldBaseAmountDecimal,
                        bankAccountDTO.getAccountIndexDTO().getBaseAmountDecimal()));

        var argumentCaptor = this.bankAccountRepositoryMockFactory.verifySaveCalled(BankAccount.class);
        final BankAccount bankAccountSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountUpdateRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(oldAccountName, bankAccountSaved.getName()),
                () -> Assertions.assertNotEquals(oldBaseAmountDecimal, bankAccountSaved.getBaseAmountDecimal()));
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final BankAccount bankAccount = this.unitTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, bankAccount);

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);
        });
    }

    @Test
    void bankAccountNotExistException() {

        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.bankAccountRepositoryMockFactory.mockFindById(bankAccountId, Optional.empty());

        BankAccountUpdateRequest bankAccountUpdateRequest = new BankAccountUpdateRequest();

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);
        });
    }
}
