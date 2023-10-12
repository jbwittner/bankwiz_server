package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.User;

public interface UserSpi {
    User findById(Long id);
}
