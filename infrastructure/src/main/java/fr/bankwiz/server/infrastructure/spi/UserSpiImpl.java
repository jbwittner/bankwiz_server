package fr.bankwiz.server.infrastructure.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Component
public class UserSpiImpl implements UserSpi {

    private Map<String, User> userMap = new HashMap<>();

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
        userMap.put(user.getAuthId(), user);
        return user;
    }
}
