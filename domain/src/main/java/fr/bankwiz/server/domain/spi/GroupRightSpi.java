package fr.bankwiz.server.domain.spi;

import java.util.List;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.User;

public interface GroupRightSpi {
    GroupRightDomain save(GroupRightDomain groupRight);

    List<GroupRightDomain> findByUser(User user);

    List<GroupRightDomain> findByGroup(GroupDomain group);

    void deleteByGroupAndUser(GroupDomain group, User user);

    void deleteAllByGroup(GroupDomain group);
}
