package fr.bankwiz.server.domain.spi;

import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.UserDomain;

public interface UserSpi {
    Optional<UserDomain> findByAuthId(String authId);

    UserDomain save(UserDomain user);

    Optional<UserDomain> findById(UUID id);
}
