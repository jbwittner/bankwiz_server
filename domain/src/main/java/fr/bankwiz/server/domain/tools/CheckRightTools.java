package fr.bankwiz.server.domain.tools;

import fr.bankwiz.server.domain.exception.UserNoReadRightException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;

public class CheckRightTools {

    private final GroupRightSpi groupRightSpi;
    private final AuthenticationSpi authenticationSpi;

    public CheckRightTools(GroupRightSpi groupRightSpi, AuthenticationSpi authenticationSpi) {
        this.groupRightSpi = groupRightSpi;
        this.authenticationSpi = authenticationSpi;
    }

    public boolean hasRight(final UserDomain user, final GroupDomain group, final GroupRightEnum right) {
        return this.groupRightSpi.findByGroup(group).stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .anyMatch(p -> p.getGroupRightEnum().equals(right));
    }

    public boolean hasAnyRight(final UserDomain user, final GroupDomain group) {
        return this.groupRightSpi.findByGroup(group).stream()
                .anyMatch(p -> p.getUser().getId().equals(user.getId()));
    }

    public boolean isAdmin(final UserDomain user, final GroupDomain group) {
        return this.hasRight(user, group, GroupRightEnum.ADMIN);
    }

    public boolean canWrite(final UserDomain user, final GroupDomain group) {
        return this.hasRight(user, group, GroupRightEnum.WRITE) || isAdmin(user, group);
    }

    public boolean canRead(final UserDomain user, final GroupDomain group) {
        return this.hasRight(user, group, GroupRightEnum.READ) || canWrite(user, group);
    }

    public void checkCanWrite(final UserDomain user, final GroupDomain group) {
        if (!this.canWrite(user, group)) {
            throw new UserNoWriteRightException(user, group);
        }
    }

    public void checkCanRead(final UserDomain user, final GroupDomain group) {
        if (!this.canRead(user, group)) {
            throw new UserNoReadRightException(user, group);
        }
    }

    public void checkIsAdmin(final UserDomain user, final GroupDomain group) {
        if (!this.isAdmin(user, group)) {
            throw new UserNotAdminException(user, group);
        }
    }

    public void checkCurrentUserCanWrite(final GroupDomain group) {
        final UserDomain user = this.authenticationSpi.getCurrentUser();
        this.checkCanWrite(user, group);
    }

    public void checkCurrentUserCanRead(final GroupDomain group) {
        final UserDomain user = this.authenticationSpi.getCurrentUser();
        this.checkCanRead(user, group);
    }
}
