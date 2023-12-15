package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.List;

import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public interface BankAccountEntityRepository extends GroupRelatedEntityRepository<BankAccountEntity> {
    List<BankAccountEntity> findByGroupEntity(GroupEntity groupEntity);
}
