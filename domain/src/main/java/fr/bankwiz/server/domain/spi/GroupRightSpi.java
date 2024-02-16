package fr.bankwiz.server.domain.spi;

import java.util.List;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.User;

public interface GroupRightSpi {
    GroupRight save(GroupRight groupRight);

    List<GroupRight> findByUser(User user);

    List<GroupRight> findByGroup(GroupDomain group);

    void deleteByGroupAndUser(GroupDomain group, User user);

    void deleteAllByGroup(GroupDomain group);
}
