package fr.bankwiz.server.domain.spi;

import java.util.Optional;

import fr.bankwiz.server.domain.model.UserDomain;

public interface UserDomainSpi {
    UserDomain save(UserDomain userDomain);

    Optional<UserDomain> findByAuthId(String authId);
}
