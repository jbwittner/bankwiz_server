package fr.bankwiz.server.infrastructure.testhelper.mock.repository;

import java.util.List;
import java.util.UUID;

import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;

public class GroupRightEntityRepositoryMockFactory
        extends AbstractRepositoryMockFactory<GroupRightEntity, GroupRightEntityRepository, UUID> {
    public GroupRightEntityRepositoryMockFactory() {
        super(GroupRightEntityRepository.class);
    }

    public GroupRightEntityRepositoryMockFactory mockFindByUserEntity(final List<GroupRightEntity> groupRightEntities) {
        Mockito.when(this.repository.findByUserEntity(Mockito.any(UserEntity.class)))
                .thenReturn(groupRightEntities);
        return this;
    }
}
