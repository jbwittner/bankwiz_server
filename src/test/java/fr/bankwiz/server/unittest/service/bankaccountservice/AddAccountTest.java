package fr.bankwiz.server.unittest.service.bankaccountservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.BankAccountService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class AddAccountTest extends UnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountService = new BankAccountService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    void addAccountOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(
                group.getUserGroupId(),
                this.faker.pokemon().name(),
                this.faker.random().nextInt(Integer.MAX_VALUE));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccountCreationRequest.getGroupId(), group);
        this.bankAccountRepositoryMockFactory.mockSave();

        final BankAccountDTO result = this.bankAccountService.addAccount(bankAccountCreationRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getAccountName(),
                        result.getAccountIndexDTO().getAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBaseAmountDecimal(),
                        result.getAccountIndexDTO().getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getGroupId(),
                        result.getGroupIndexDTO().getGroupId()));

        var argumentCaptor = this.bankAccountRepositoryMockFactory.verifySaveCalled(BankAccount.class);
        final BankAccount bankAccountSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountCreationRequest.getAccountName(), bankAccountSaved.getName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBaseAmountDecimal(), bankAccountSaved.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getGroupId(),
                        bankAccountSaved.getGroup().getUserGroupId()));
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(bankAccountCreationRequest.getGroupId(), group);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.bankAccountService.addAccount(bankAccountCreationRequest);
        });
    }

    @Test
    void groupNotExistException() {
        BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest();
        this.groupRepositoryMockFactory.mockFindById(bankAccountCreationRequest.getGroupId(), Optional.empty());

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.bankAccountService.addAccount(bankAccountCreationRequest);
        });
    }
}
