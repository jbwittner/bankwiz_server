package fr.bankwiz.server.infrastructure.unittest.spi.bankaccountspiimpl;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByIdTest extends InfrastructureUnitTestBase {

    private BankAccountSpi bankAccountSpi;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountSpi = this.buildBankAccountSpiImpl();
    }

    @Test
    void entityExist() {
        final BankAccountEntity bankAccountEntity = this.factory.getBankAccountEntity();
        final UUID id = bankAccountEntity.getId();
        this.bankAccountRepositoryMockFactory.mockFindById(id, bankAccountEntity);

        final Optional<BankAccount> optionalBankAccount = this.bankAccountSpi.findById(id);

        Assertions.assertTrue(optionalBankAccount.isPresent());

        final BankAccount bankAccount = optionalBankAccount.get();

        final BankAccountEntity bankAccountEntityToCheck = BankAccountTransformer.toBankAccountEntity(bankAccount);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountEntity.getBaseAmountDecimal(), bankAccountEntityToCheck.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountEntity.getBankAccountName(), bankAccountEntityToCheck.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccountEntity.getId(), bankAccountEntityToCheck.getId()),
                () -> Assertions.assertEquals(
                        bankAccountEntity.getGroupEntity(), bankAccountEntityToCheck.getGroupEntity()));
    }

    @Test
    void entityNotExist() {
        final UUID id = UUID.randomUUID();
        this.bankAccountRepositoryMockFactory.mockFindById(id, Optional.empty());
        final Optional<BankAccount> optionalBankAccount = this.bankAccountSpi.findById(id);
        Assertions.assertTrue(optionalBankAccount.isEmpty());
    }
}
