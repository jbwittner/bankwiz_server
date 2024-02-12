package fr.bankwiz.server.infrastructure.unittest.spi.transactionspiimpl;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class DeleteByIdTest extends InfrastructureUnitTestBase {

    private TransactionSpi transactionSpi;

    @Override
    protected void initDataBeforeEach() {
        this.transactionSpi = this.buildTransactionSpiImpl();
    }

    @Test
    void deleteOk() {
        final TransactionEntity transactionEntity = this.factory.getTransactionEntity();
        final UUID uuid = transactionEntity.getId();

        this.transactionRepositoryMockFactory.mockDelete(transactionEntity);

        this.transactionSpi.deleteById(uuid);

        this.transactionRepositoryMockFactory.verifyDeleteByIdCalled(uuid, 1);
    }
}
