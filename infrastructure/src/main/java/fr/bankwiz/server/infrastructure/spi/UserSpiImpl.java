package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.model.spi.UserDomainSpi;

@Component
public class UserSpiImpl implements UserDomainSpi {
    @Override
    public UserDomain save(final UserDomain userDomain) {
        return null;
    }

    @Override
    public Optional<UserDomain> findByAuthId(final String authId) {
        return Optional.empty();
    }
}
