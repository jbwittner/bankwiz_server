package fr.bankwiz.server.domain.spi;

import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.User;

public interface UserSpi {
    Optional<User> findByAuthId(String authId);
    User save(User user);
    Optional<User> findById(UUID id);
}
