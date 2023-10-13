package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

@Component
public class UserSpiImpl implements UserSpi {

    @Override
    public Optional<User> findByAuthId(String authId) {
        throw new UnsupportedOperationException("Unimplemented method 'findByAuthId'");
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
