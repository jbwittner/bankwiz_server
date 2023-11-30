package fr.bankwiz.server.infrastructure.testhelper.mock.repository;

import java.util.UUID;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountRepository;

public class BankAccountRepositoryMockFactory
        extends AbstractRepositoryMockFactory<BankAccountEntity, BankAccountRepository, UUID> {
    public BankAccountRepositoryMockFactory() {
        super(BankAccountRepository.class);
    }
}
