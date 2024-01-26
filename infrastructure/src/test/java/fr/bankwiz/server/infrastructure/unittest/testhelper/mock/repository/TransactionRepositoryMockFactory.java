package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.UUID;

import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;

public class TransactionRepositoryMockFactory
        extends AbstractRepositoryMockFactory<TransactionEntity, TransactionEntityRepository, UUID> {
    public TransactionRepositoryMockFactory() {
        super(TransactionEntityRepository.class);
    }

    {
    }
}
