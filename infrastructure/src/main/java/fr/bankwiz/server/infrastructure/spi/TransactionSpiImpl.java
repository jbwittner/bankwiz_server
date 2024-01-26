package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;

@Component
public class TransactionSpiImpl implements TransactionSpi {

    private TransactionEntityRepository transactionEntityRepository;

    public TransactionSpiImpl(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        final TransactionEntity transactionEntity = TransactionTransformer.toTransactionEntity(transaction);
        final TransactionEntity transactionEntitySaved = this.transactionEntityRepository.save(transactionEntity);
        return TransactionTransformer.fromTransactionEntity(transactionEntitySaved);
    }
}
