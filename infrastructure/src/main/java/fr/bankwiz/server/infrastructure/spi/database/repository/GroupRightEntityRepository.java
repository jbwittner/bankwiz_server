package fr.bankwiz.server.infrastructure.spi.database.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public interface GroupRightEntityRepository extends JpaRepository<GroupRightEntity, UUID> {
    List<GroupRightEntity> findByUserEntity(UserEntity userEntity);
    List<GroupRightEntity> findByGroupEntity(GroupEntity groupEntity);
}
