package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

@Component
public class UserSpiImpl implements UserSpi {

    @Override
    public Optional<User> findByAuthId(String authId) {
        User user = User.builder().authId("authId").email("email").userUuid(UUID.randomUUID()).build();
        return Optional.of(user);
    }

    @Override
    public User save(User user) {
        return user;
    }
}
