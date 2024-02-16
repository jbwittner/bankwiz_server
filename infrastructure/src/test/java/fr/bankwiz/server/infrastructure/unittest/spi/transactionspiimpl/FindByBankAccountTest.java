package fr.bankwiz.server.infrastructure.unittest.spi.transactionspiimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByBankAccountTest extends InfrastructureUnitTestBase {

    private TransactionSpi transactionSpi;

    @Override
    protected void initDataBeforeEach() {
        this.transactionSpi = this.buildTransactionSpiImpl();
    }

    @Test
    void findByBankAccount() {

        final BankAccountEntity bankAccountEntity = this.factory.getBankAccountEntity();
        final List<TransactionEntity> transactionEntities = new ArrayList<>();
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));

        this.transactionRepositoryMockFactory.mockFindByBankAccountEntity(bankAccountEntity, transactionEntities);

        final BankAccountDomain bankAccount = BankAccountTransformer.fromBankAccountEntity(bankAccountEntity);

        final var transations = this.transactionSpi.findByBankAccount(bankAccount);

        Assertions.assertAll(() -> Assertions.assertEquals(transactionEntities.size(), transations.size()), () -> {
            transations.forEach(transaction -> {
                TransactionEntity transactionEntityFinded = transactionEntities.stream()
                        .filter(transactionEntity -> transactionEntity.getId().equals(transaction.getId()))
                        .findFirst()
                        .orElseThrow();
                Assertions.assertAll(
                        () -> Assertions.assertEquals(
                                transactionEntityFinded.getDecimalAmount(), transaction.getDecimalAmount()),
                        () -> Assertions.assertEquals(transactionEntityFinded.getComment(), transaction.getComment()),
                        () -> Assertions.assertEquals(
                                transactionEntityFinded.getBankAccountEntity(),
                                BankAccountTransformer.toBankAccountEntity(transaction.getBankAccount())),
                        () -> Assertions.assertEquals(transactionEntityFinded.getId(), transaction.getId()));
            });
        });
    }
}
