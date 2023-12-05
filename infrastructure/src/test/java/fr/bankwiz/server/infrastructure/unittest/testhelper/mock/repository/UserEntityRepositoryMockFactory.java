package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.Optional;
import java.util.UUID;

import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

public class UserEntityRepositoryMockFactory
        extends AbstractRepositoryMockFactory<UserEntity, UserEntityRepository, UUID> {
    public UserEntityRepositoryMockFactory() {
        super(UserEntityRepository.class);
    }

    public UserEntityRepositoryMockFactory mockFindByAuthId(String authId, Optional<UserEntity> optionalUserEntity) {
        Mockito.when(repository.findByAuthId(authId)).thenReturn(optionalUserEntity);
        return this;
    }
}
