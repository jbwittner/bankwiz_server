package fr.bankwiz.server.domain.spi;

import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.GroupDomain;

public interface GroupSpi {
    GroupDomain save(GroupDomain group);

    Optional<GroupDomain> findById(UUID id);

    void deleteById(UUID id);
}
