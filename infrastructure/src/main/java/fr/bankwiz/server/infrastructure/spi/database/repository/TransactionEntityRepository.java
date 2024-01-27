package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByBankAccountEntity(BankAccountEntity bankAccountEntity);
}
