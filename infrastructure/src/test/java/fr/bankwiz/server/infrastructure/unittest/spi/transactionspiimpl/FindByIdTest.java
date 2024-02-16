package fr.bankwiz.server.infrastructure.unittest.spi.transactionspiimpl;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByIdTest extends InfrastructureUnitTestBase {

    private TransactionSpi transactionSpi;

    @Override
    protected void initDataBeforeEach() {
        this.transactionSpi = this.buildTransactionSpiImpl();
    }

    @Test
    void findTransaction() {
        final TransactionEntity transactionEntity = this.factory.getTransactionEntity();
        final UUID uuid = transactionEntity.getId();
        this.transactionRepositoryMockFactory.mockFindById(uuid, Optional.of(transactionEntity));

        final Optional<TransactionDomain> optional = this.transactionSpi.findById(uuid);

        Assertions.assertTrue(optional.isPresent());

        final TransactionDomain transaction = optional.get();

        final TransactionEntity transactionEntityFinded = TransactionTransformer.toTransactionEntity(transaction);

        Assertions.assertEquals(transactionEntity, transactionEntityFinded);
    }

    @Test
    void transactionNotExist() {
        final UUID uuid = UUID.randomUUID();
        this.transactionRepositoryMockFactory.mockFindById(uuid, Optional.empty());

        final Optional<TransactionDomain> optional = this.transactionSpi.findById(uuid);

        Assertions.assertTrue(optional.isEmpty());
    }
}
