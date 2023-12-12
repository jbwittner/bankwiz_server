package fr.bankwiz.server.infrastructure.unittest.transformer.bankaccounttransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToBankAccountIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void toBankAccountIndexDTOSingle() {
        final Group group = this.factory.getGroup();
        final BankAccount bankAccount = this.factory.getBankAccount(group);
        final BankAccountIndexDTO bankAccountIndexDTO = BankAccountTransformer.toBankAccountIndexDTO(bankAccount);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
    }

    @Test
    void toBankAccountIndexDTOList() {
        final Group group = this.factory.getGroup();
        final List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(this.factory.getBankAccount(group));
        bankAccounts.add(this.factory.getBankAccount(group));
        final List<BankAccountIndexDTO> bankAccountIndexDTOs =
                BankAccountTransformer.toBankAccountIndexDTO(bankAccounts);

        Assertions.assertEquals(bankAccounts.size(), bankAccountIndexDTOs.size());

        bankAccounts.stream().forEach(bankAccount -> {
            Optional<BankAccountIndexDTO> optional = bankAccountIndexDTOs.stream()
                    .filter(bankAccountIndexDTO ->
                            bankAccountIndexDTO.getBankAccountId().equals(bankAccount.getId()))
                    .findFirst();
            Assertions.assertTrue(optional.isPresent());

            final BankAccountIndexDTO bankAccountIndexDTO = optional.get();

            Assertions.assertAll(
                    () -> Assertions.assertEquals(
                            bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                    () -> Assertions.assertEquals(bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
        });
    }
}
