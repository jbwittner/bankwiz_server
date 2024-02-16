package fr.bankwiz.server.infrastructure.unittest.spi.transactionspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class SaveTest extends InfrastructureUnitTestBase {

    private TransactionSpi transactionSpi;

    @Override
    protected void initDataBeforeEach() {
        this.transactionSpi = this.buildTransactionSpiImpl();
    }

    @Test
    void save() {

        this.transactionRepositoryMockFactory.mockSave();

        final TransactionDomain transaction = this.factory.getTransaction();

        final TransactionDomain transactionSaved = this.transactionSpi.save(transaction);

        final var argumentCaptor = this.transactionRepositoryMockFactory.verifySaveCalled(TransactionEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        TransactionTransformer.toTransactionEntity(transaction), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount().getId(),
                        transactionSaved.getBankAccount().getId()),
                () -> Assertions.assertEquals(transaction.getDecimalAmount(), transactionSaved.getDecimalAmount()),
                () -> Assertions.assertEquals(transaction.getComment(), transactionSaved.getComment()),
                () -> Assertions.assertEquals(transaction.getId(), transactionSaved.getId()));
    }
}
