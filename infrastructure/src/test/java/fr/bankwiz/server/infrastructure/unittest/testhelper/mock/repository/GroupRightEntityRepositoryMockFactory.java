package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
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

    public GroupRightEntityRepositoryMockFactory mockFindByGroupEntity(
            final List<GroupRightEntity> groupRightEntities) {
        Mockito.when(this.repository.findByGroupEntity(Mockito.any(GroupEntity.class)))
                .thenReturn(groupRightEntities);
        return this;
    }

    public GroupRightEntityRepositoryMockFactory verifyFindByUserEntity(final UserEntity userEntityToCheck) {
        ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(this.repository).findByUserEntity(argument.capture());
        Assertions.assertEquals(userEntityToCheck, argument.getValue());
        return this;
    }

    public GroupRightEntityRepositoryMockFactory verifyFindByGroupEntity(final GroupEntity groupEntityToCheck) {
        ArgumentCaptor<GroupEntity> argument = ArgumentCaptor.forClass(GroupEntity.class);
        Mockito.verify(this.repository).findByGroupEntity(argument.capture());
        Assertions.assertEquals(groupEntityToCheck, argument.getValue());
        return this;
    }
}
