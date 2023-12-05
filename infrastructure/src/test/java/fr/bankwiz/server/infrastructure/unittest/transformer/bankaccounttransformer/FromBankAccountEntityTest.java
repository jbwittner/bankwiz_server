package fr.bankwiz.server.infrastructure.unittest.transformer.bankaccounttransformer;

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
    void ok() {
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
}
