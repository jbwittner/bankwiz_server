package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRelatedEntity;

@NoRepositoryBean
public interface GroupRelatedEntityRepository<T extends GroupRelatedEntity> extends JpaRepository<T, UUID> {
    List<T> findByGroupEntity(GroupEntity groupEntity);

    boolean existsByGroupEntity(GroupEntity groupEntity);
}
