package fr.bankwiz.server.infrastructure.spi.bankaccountspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;

class SaveTest extends InfrastructureUnitTestBase {

    private BankAccountSpi bankAccountSpi;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountSpi = this.buildBankAccountSpiImpl();
    }

    @Test
    void save() {

        this.bankAccountRepositoryMockFactory.mockSave();

        final Group group = this.factory.getGroup();
        final BankAccount bankAccount = this.factory.getBankAccount(group);

        final BankAccount bankAccountSaved = this.bankAccountSpi.save(bankAccount);

        final var argumentCaptor = this.bankAccountRepositoryMockFactory.verifySaveCalled(BankAccountEntity.class);

        Assertions.assertAll(
                () -> {
                    final BankAccountEntity bankAccountEntityExpected =
                            BankAccountTransformer.toBankAccountEntity(bankAccount);
                    final BankAccountEntity bankAccountEntity = argumentCaptor.getValue();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(
                                    bankAccountEntityExpected.getBaseAmountDecimal(),
                                    bankAccountEntity.getBaseAmountDecimal()),
                            () -> Assertions.assertEquals(
                                    bankAccountEntityExpected.getBankAccountName(),
                                    bankAccountEntity.getBankAccountName()),
                            () -> Assertions.assertEquals(
                                    bankAccountEntityExpected.getGroupEntity(), bankAccountEntity.getGroupEntity()),
                            () -> Assertions.assertEquals(
                                    bankAccountEntityExpected.getId(), bankAccountEntity.getId()));
                },
                () -> Assertions.assertEquals(
                        bankAccount.getDecimalBaseAmount(), bankAccountSaved.getDecimalBaseAmount()),
                () -> Assertions.assertEquals(bankAccount.getBankAccountName(), bankAccountSaved.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getGroup(), bankAccountSaved.getGroup()),
                () -> Assertions.assertEquals(bankAccount.getId(), bankAccountSaved.getId()));
    }
}
