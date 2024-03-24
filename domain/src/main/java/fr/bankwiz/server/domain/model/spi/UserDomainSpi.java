package fr.bankwiz.server.domain.model.spi;

import java.util.Optional;

import fr.bankwiz.server.domain.model.model.UserDomain;

public interface UserDomainSpi {
    UserDomain save(UserDomain user);

    Optional<UserDomain> findByAuthId(String authId);
}
