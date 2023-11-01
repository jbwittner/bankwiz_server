package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, UUID> {}
