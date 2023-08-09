package fr.bankwiz.server.service.bankaccountservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.BankAccountService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class GetBankAccountsTest extends UnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountService = new BankAccountService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    void getBankAccountsOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group1 = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount11 = this.unitTestFactory.getBankAccount(group1);
        final BankAccount bankAccount12 = this.unitTestFactory.getBankAccount(group1);
        final Group group2 = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount21 = this.unitTestFactory.getBankAccount(group2);
        final BankAccount bankAccount22 = this.unitTestFactory.getBankAccount(group2);
        final BankAccount bankAccount23 = this.unitTestFactory.getBankAccount(group2);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        final var result = this.bankAccountService.getBankAccounts();

        Assertions.assertEquals(2, result.size());

        BankAccountGroupDTO bankAccountGroupDTO1 = result.stream()
                .filter(bankAccountGroupDTO ->
                        bankAccountGroupDTO.getGroupIndexDTO().getGroupId().equals(group1.getUserGroupId()))
                .findFirst()
                .orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        group1.getUserGroupId(),
                        bankAccountGroupDTO1.getGroupIndexDTO().getGroupId()),
                () -> Assertions.assertEquals(
                        2, bankAccountGroupDTO1.getAccountIndexDTOs().size()),
                () -> {
                    var accountIndexDTOs = bankAccountGroupDTO1.getAccountIndexDTOs();

                    BankAccountIndexDTO bankAccountIndexDTO1 = accountIndexDTOs.stream()
                            .filter(bankAccountIndexDTO ->
                                    bankAccountIndexDTO.getAccountId().equals(bankAccount11.getId()))
                            .findFirst()
                            .orElseThrow();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(bankAccount11.getId(), bankAccountIndexDTO1.getAccountId()),
                            () -> Assertions.assertEquals(
                                    bankAccount11.getBaseAmountDecimal(), bankAccountIndexDTO1.getBaseAmountDecimal()));

                    BankAccountIndexDTO bankAccountIndexDTO2 = accountIndexDTOs.stream()
                            .filter(bankAccountIndexDTO ->
                                    bankAccountIndexDTO.getAccountId().equals(bankAccount12.getId()))
                            .findFirst()
                            .orElseThrow();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(bankAccount12.getId(), bankAccountIndexDTO2.getAccountId()),
                            () -> Assertions.assertEquals(
                                    bankAccount12.getBaseAmountDecimal(), bankAccountIndexDTO2.getBaseAmountDecimal()));
                });

        BankAccountGroupDTO bankAccountGroupDTO2 = result.stream()
                .filter(bankAccountGroupDTO ->
                        bankAccountGroupDTO.getGroupIndexDTO().getGroupId().equals(group2.getUserGroupId()))
                .findFirst()
                .orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        group2.getUserGroupId(),
                        bankAccountGroupDTO2.getGroupIndexDTO().getGroupId()),
                () -> Assertions.assertEquals(
                        3, bankAccountGroupDTO2.getAccountIndexDTOs().size()),
                () -> {
                    var accountIndexDTOs = bankAccountGroupDTO2.getAccountIndexDTOs();

                    BankAccountIndexDTO bankAccountIndexDTO1 = accountIndexDTOs.stream()
                            .filter(bankAccountIndexDTO ->
                                    bankAccountIndexDTO.getAccountId().equals(bankAccount21.getId()))
                            .findFirst()
                            .orElseThrow();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(bankAccount21.getId(), bankAccountIndexDTO1.getAccountId()),
                            () -> Assertions.assertEquals(
                                    bankAccount21.getBaseAmountDecimal(), bankAccountIndexDTO1.getBaseAmountDecimal()));

                    BankAccountIndexDTO bankAccountIndexDTO2 = accountIndexDTOs.stream()
                            .filter(bankAccountIndexDTO ->
                                    bankAccountIndexDTO.getAccountId().equals(bankAccount22.getId()))
                            .findFirst()
                            .orElseThrow();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(bankAccount22.getId(), bankAccountIndexDTO2.getAccountId()),
                            () -> Assertions.assertEquals(
                                    bankAccount22.getBaseAmountDecimal(), bankAccountIndexDTO2.getBaseAmountDecimal()));

                    BankAccountIndexDTO bankAccountIndexDTO3 = accountIndexDTOs.stream()
                            .filter(bankAccountIndexDTO ->
                                    bankAccountIndexDTO.getAccountId().equals(bankAccount23.getId()))
                            .findFirst()
                            .orElseThrow();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(bankAccount23.getId(), bankAccountIndexDTO3.getAccountId()),
                            () -> Assertions.assertEquals(
                                    bankAccount23.getBaseAmountDecimal(), bankAccountIndexDTO3.getBaseAmountDecimal()));
                });
    }
}
