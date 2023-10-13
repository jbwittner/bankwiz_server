package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

@Component
public class UserSpiImpl implements UserSpi {

    @Override
    public User findById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
