package fr.bankwiz.server.domain.spi;

import java.util.Optional;

import fr.bankwiz.server.domain.model.User;

public interface UserSpi {
    Optional<User> findByAuthId(String authId);

    User save(User user);
}
