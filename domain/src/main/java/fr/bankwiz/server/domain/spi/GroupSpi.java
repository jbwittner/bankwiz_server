package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.Group;

public interface GroupSpi {
    Group save(Group group);
}
