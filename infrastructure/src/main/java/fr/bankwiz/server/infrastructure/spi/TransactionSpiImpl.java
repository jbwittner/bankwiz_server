package fr.bankwiz.server.infrastructure.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
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

    @Override
    public List<Transaction> findByBankAccount(BankAccount bankAccount) {
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);
        final var transactionEntities = this.transactionEntityRepository.findByBankAccountEntity(bankAccountEntity);
        return TransactionTransformer.fromTransactionEntity(transactionEntities);
    }

    @Override
    public Optional<Transaction> findById(UUID uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void deleteById(UUID uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
