package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.UUID;

import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;

public class BankAccountRepositoryMockFactory
        extends AbstractRepositoryMockFactory<BankAccountEntity, BankAccountEntityRepository, UUID> {
    public BankAccountRepositoryMockFactory() {
        super(BankAccountEntityRepository.class);
    }

    public BankAccountRepositoryMockFactory mockExistByGroupEntity(GroupEntity groupEntity, boolean exist) {
        Mockito.when(repository.existsByGroupEntity(groupEntity)).thenReturn(exist);
        return this;
    }
}
