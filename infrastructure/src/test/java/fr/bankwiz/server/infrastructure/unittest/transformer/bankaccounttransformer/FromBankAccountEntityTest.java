package fr.bankwiz.server.infrastructure.unittest.transformer.bankaccounttransformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FromBankAccountEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void single() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final BankAccountEntity bankAccountEntity = this.factory.getBankAccountEntity(groupEntity);
        final BankAccount bankAccount = BankAccountTransformer.fromBankAccountEntity(bankAccountEntity);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountEntity.getBaseAmountDecimal(), bankAccount.getDecimalBaseAmount()),
                () -> Assertions.assertEquals(bankAccountEntity.getBankAccountName(), bankAccount.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccountEntity.getId(), bankAccount.getId()),
                () -> Assertions.assertEquals(
                        bankAccountEntity.getGroupEntity(), GroupTransformer.toGroupEntity(bankAccount.getGroup())));
    }

    @Test
    void list() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final List<BankAccountEntity> bankAccountEntities = new ArrayList<>();
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));

        final var bankAccounts = BankAccountTransformer.fromBankAccountEntity(bankAccountEntities);

        Assertions.assertEquals(bankAccountEntities.size(), bankAccountEntities.size());

        bankAccounts.forEach(bankAccount -> {
            final BankAccountEntity result = bankAccountEntities.stream()
                    .filter(bankAccountEntity -> bankAccountEntity.getId().equals(bankAccount.getId()))
                    .findFirst()
                    .orElseThrow();

            Assertions.assertAll(
                    () -> Assertions.assertEquals(result.getBaseAmountDecimal(), bankAccount.getDecimalBaseAmount()),
                    () -> Assertions.assertEquals(result.getBankAccountName(), bankAccount.getBankAccountName()),
                    () -> Assertions.assertEquals(result.getId(), bankAccount.getId()),
                    () -> Assertions.assertEquals(
                            result.getGroupEntity(), GroupTransformer.toGroupEntity(bankAccount.getGroup())));
        });
    }
}
