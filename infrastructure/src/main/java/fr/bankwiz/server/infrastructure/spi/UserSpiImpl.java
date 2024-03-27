package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.model.spi.UserDomainSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

@Component
public class UserSpiImpl implements UserDomainSpi {

    private final UserEntityRepository userEntityRepository;

    public UserSpiImpl(final UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDomain save(final UserDomain userDomain) {
        final UserEntity userEntity = new UserEntity(userDomain);
        final UserEntity userEntitySaved = this.userEntityRepository.save(userEntity);
        return userEntitySaved.toUserDomain();
    }

    @Override
    public Optional<UserDomain> findByAuthId(final String authId) {
        final Optional<UserEntity> optional = this.userEntityRepository.findByAuthId(authId);
        if (optional.isPresent()) {
            final UserDomain userDomain = optional.get().toUserDomain();
            return Optional.of(userDomain);
        } else {
            return Optional.empty();
        }
    }
}
