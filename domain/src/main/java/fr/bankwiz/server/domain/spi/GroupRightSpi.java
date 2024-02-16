package fr.bankwiz.server.domain.spi;

import java.util.List;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;

public interface GroupRightSpi {
    GroupRightDomain save(GroupRightDomain groupRight);

    List<GroupRightDomain> findByUser(UserDomain user);

    List<GroupRightDomain> findByGroup(GroupDomain group);

    void deleteByGroupAndUser(GroupDomain group, UserDomain user);

    void deleteAllByGroup(GroupDomain group);
}
