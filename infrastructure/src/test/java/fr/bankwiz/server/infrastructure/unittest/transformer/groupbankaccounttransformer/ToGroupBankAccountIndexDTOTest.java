package fr.bankwiz.server.infrastructure.unittest.transformer.groupbankaccounttransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupBankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;
import fr.bankwiz.server.infrastructure.transformer.GroupBankAccountTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupBankAccountIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void compareBankAccountIndexDTO(
            GroupBankAccount groupBankAccount, GroupBankAccountIndexDTO groupBankAccountIndexDTO) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        groupBankAccount.getGroup().getId(),
                        groupBankAccountIndexDTO.getGroupeIndex().getGroupId()),
                () -> Assertions.assertEquals(
                        groupBankAccount.getGroup().getGroupName(),
                        groupBankAccountIndexDTO.getGroupeIndex().getGroupName()),
                () -> Assertions.assertEquals(
                        groupBankAccount.getBankAccounts().size(),
                        groupBankAccountIndexDTO.getBankAccountIndexList().size()),
                () -> {
                    groupBankAccountIndexDTO.getBankAccountIndexList().stream().forEach(bankAccountIndexDTO -> {
                        Optional<BankAccountDomain> optional = groupBankAccount.getBankAccounts().stream()
                                .filter(bankAccount ->
                                        bankAccount.getId().equals(bankAccountIndexDTO.getBankAccountId()))
                                .findFirst();
                        Assertions.assertTrue(optional.isPresent());
                        BankAccountDomain bankAccount = optional.get();
                        Assertions.assertAll(
                                () -> Assertions.assertEquals(
                                        bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                                () -> Assertions.assertEquals(
                                        bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
                    });
                });
    }

    @Test
    void toBankAccountIndexDTOSingle() {
        final Group group = this.factory.getGroup();
        final List<BankAccountDomain> bankAccounts = new ArrayList<>();
        bankAccounts.add(this.factory.getBankAccount(group));
        bankAccounts.add(this.factory.getBankAccount(group));
        bankAccounts.add(this.factory.getBankAccount(group));

        final GroupBankAccount groupBankAccount = GroupBankAccount.builder()
                .bankAccounts(bankAccounts)
                .group(group)
                .build();

        final GroupBankAccountIndexDTO groupBankAccountIndexDTO =
                GroupBankAccountTransformer.toGroupBankAccountIndexDTO(groupBankAccount);

        this.compareBankAccountIndexDTO(groupBankAccount, groupBankAccountIndexDTO);
    }

    @Test
    void toBankAccountIndexDTOSList() {
        final Group groupOne = this.factory.getGroup();
        final List<BankAccountDomain> bankAccountsOne = new ArrayList<>();
        bankAccountsOne.add(this.factory.getBankAccount(groupOne));
        bankAccountsOne.add(this.factory.getBankAccount(groupOne));
        bankAccountsOne.add(this.factory.getBankAccount(groupOne));

        final GroupBankAccount groupBankAccountOne = GroupBankAccount.builder()
                .bankAccounts(bankAccountsOne)
                .group(groupOne)
                .build();

        final Group groupTwo = this.factory.getGroup();
        final List<BankAccountDomain> bankAccountsTwo = new ArrayList<>();
        bankAccountsTwo.add(this.factory.getBankAccount(groupTwo));
        bankAccountsTwo.add(this.factory.getBankAccount(groupTwo));
        bankAccountsTwo.add(this.factory.getBankAccount(groupTwo));

        final GroupBankAccount groupBankAccountTwo = GroupBankAccount.builder()
                .bankAccounts(bankAccountsTwo)
                .group(groupTwo)
                .build();

        final List<GroupBankAccount> groupBankAccounts = new ArrayList<>();
        groupBankAccounts.add(groupBankAccountOne);
        groupBankAccounts.add(groupBankAccountTwo);

        final List<GroupBankAccountIndexDTO> groupBankAccountIndexDTOs =
                GroupBankAccountTransformer.toGroupBankAccountIndexDTO(groupBankAccounts);

        Assertions.assertEquals(groupBankAccounts.size(), groupBankAccountIndexDTOs.size());

        groupBankAccountIndexDTOs.stream().forEach(groupBankAccountIndexDTO -> {
            Optional<GroupBankAccount> optional = groupBankAccounts.stream()
                    .filter(groupBankAccount -> groupBankAccount
                            .getGroup()
                            .getId()
                            .equals(groupBankAccountIndexDTO.getGroupeIndex().getGroupId()))
                    .findFirst();
            Assertions.assertTrue(optional.isPresent());
            GroupBankAccount groupBankAccount = optional.get();
            this.compareBankAccountIndexDTO(groupBankAccount, groupBankAccountIndexDTO);
        });
    }
}
