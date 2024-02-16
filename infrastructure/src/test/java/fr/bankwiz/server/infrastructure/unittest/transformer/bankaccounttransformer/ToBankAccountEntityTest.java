package fr.bankwiz.server.infrastructure.unittest.transformer.bankaccounttransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToBankAccountEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final Group group = this.factory.getGroup();
        final BankAccountDomain bankAccount = this.factory.getBankAccount(group);
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccount.getDecimalBaseAmount(), bankAccountEntity.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(bankAccount.getBankAccountName(), bankAccountEntity.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getId(), bankAccountEntity.getId()),
                () -> Assertions.assertEquals(
                        bankAccount.getGroup(), GroupTransformer.fromGroupEntity(bankAccountEntity.getGroupEntity())));
    }
}
