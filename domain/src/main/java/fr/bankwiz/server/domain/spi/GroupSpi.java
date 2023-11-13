package fr.bankwiz.server.domain.spi;

import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;

public interface GroupSpi {
    Group save(Group group);
    Optional<Group> findById(UUID id);
}
