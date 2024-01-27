package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.List;
import java.util.UUID;

import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;

public class TransactionRepositoryMockFactory
        extends AbstractRepositoryMockFactory<TransactionEntity, TransactionEntityRepository, UUID> {
    public TransactionRepositoryMockFactory() {
        super(TransactionEntityRepository.class);
    }

    public TransactionRepositoryMockFactory mockFindByBankAccountEntity(
            BankAccountEntity bankAccountEntity, List<TransactionEntity> transactionEntities) {
        Mockito.when(this.repository.findByBankAccountEntity(bankAccountEntity)).thenReturn(transactionEntities);
        return this;
    }
}
