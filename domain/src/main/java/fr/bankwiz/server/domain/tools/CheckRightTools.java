package fr.bankwiz.server.domain.tools;

import fr.bankwiz.server.domain.exception.UserNoReadRightException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.GroupRightSpi;

public class CheckRightTools {

    private final GroupRightSpi groupRightSpi;

    public CheckRightTools(GroupRightSpi groupRightSpi) {
        this.groupRightSpi = groupRightSpi;
    }

    public boolean hasRight(final User user, final Group group, final GroupRightEnum right) {
        return this.groupRightSpi.findByGroup(group).stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .anyMatch(p -> p.getGroupRightEnum().equals(right));
    }

    public boolean hasAnyRight(final User user, final Group group) {
        return this.groupRightSpi.findByGroup(group).stream()
                .anyMatch(p -> p.getUser().getId().equals(user.getId()));
    }

    public boolean isAdmin(final User user, final Group group) {
        return this.hasRight(user, group, GroupRightEnum.ADMIN);
    }

    public boolean canWrite(final User user, final Group group) {
        return this.hasRight(user, group, GroupRightEnum.WRITE) || isAdmin(user, group);
    }

    public boolean canRead(final User user, final Group group) {
        return this.hasRight(user, group, GroupRightEnum.READ) || canWrite(user, group);
    }

    public void checkCanWrite(final User user, final Group group) {
        if (!this.canWrite(user, group)) {
            throw new UserNoWriteRightException(user, group);
        }
    }

    public void checkCanRead(final User user, final Group group) {
        if (!this.canRead(user, group)) {
            throw new UserNoReadRightException(user, group);
        }
    }

    public void checkIsAdmin(final User user, final Group group) {
        if (!this.isAdmin(user, group)) {
            throw new UserNotAdminException(user, group);
        }
    }
}
