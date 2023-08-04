package fr.bankwiz.server.integrationtest.controller.bankaccountcontroller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

public class GetBankAccountsTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getBankAccountsOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group1 = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount11 = this.integrationTestFactory.getBankAccount(group1);
        final BankAccount bankAccount12 = this.integrationTestFactory.getBankAccount(group1);
        final Group group2 = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount21 = this.integrationTestFactory.getBankAccount(group2);
        final BankAccount bankAccount22 = this.integrationTestFactory.getBankAccount(group2);
        final BankAccount bankAccount23 = this.integrationTestFactory.getBankAccount(group2);

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNTS.getUri();

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final List<BankAccountGroupDTO> bankAccountGroupDTOs =
                IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, BankAccountGroupDTO.class);

        Assertions.assertEquals(2, bankAccountGroupDTOs.size());

        BankAccountGroupDTO bankAccountGroupDTO1 = bankAccountGroupDTOs.stream()
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

        BankAccountGroupDTO bankAccountGroupDTO2 = bankAccountGroupDTOs.stream()
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
