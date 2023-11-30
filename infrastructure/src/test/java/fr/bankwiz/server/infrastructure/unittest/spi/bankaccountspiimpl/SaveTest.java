package fr.bankwiz.server.infrastructure.unittest.spi.bankaccountspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.BankAccountSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.BankAccountRepositoryMockFactory;

class SaveTest extends InfrastructureUnitTestBase {

    private BankAccountRepositoryMockFactory bankAccountRepositoryMockFactory;
    private BankAccountSpiImpl bankAccountSpiImpl;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountRepositoryMockFactory = new BankAccountRepositoryMockFactory();
        this.bankAccountSpiImpl = new BankAccountSpiImpl(bankAccountRepositoryMockFactory.getRepository());
    }

    @Test
    void save() {

        this.bankAccountRepositoryMockFactory.mockSave();

        final Group group = this.factory.getGroup();
        final BankAccount bankAccount = this.factory.getBankAccount(group);

        final BankAccount bankAccountSaved = this.bankAccountSpiImpl.save(bankAccount);

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
