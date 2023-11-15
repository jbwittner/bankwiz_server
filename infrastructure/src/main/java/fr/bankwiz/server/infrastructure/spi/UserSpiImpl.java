package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Component
public class UserSpiImpl implements UserSpi {

    private UserEntityRepository userEntityRepository;

    public UserSpiImpl(final UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Optional<User> findByAuthId(String authId) {
        final Optional<UserEntity> optionalUserEntity = this.userEntityRepository.findByAuthId(authId);

        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }

        final UserEntity userEntity = optionalUserEntity.get();
        final User user = UserTransformer.fromUserEntity(userEntity);
        return Optional.of(user);
    }

    @Override
    public User save(User user) {
        final UserEntity userEntityToSave = UserTransformer.toUserEntity(user);
        final UserEntity userEntitySaved = this.userEntityRepository.save(userEntityToSave);
        return UserTransformer.fromUserEntity(userEntitySaved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        final Optional<UserEntity> optionalUserEntity = this.userEntityRepository.findById(id);

        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }

        final UserEntity userEntity = optionalUserEntity.get();
        final User user = UserTransformer.fromUserEntity(userEntity);
        return Optional.of(user);
    }
}
