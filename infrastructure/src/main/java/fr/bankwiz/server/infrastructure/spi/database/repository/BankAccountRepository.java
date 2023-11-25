package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {}
