package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;

public class UserNotAdminException extends FunctionalException {

    public UserNotAdminException(final User user, final Group group) {
        super(MessageFormat.format("The user {0} is not an administrator of group {1}", user.getId(), group.getId()));
        this.attributes.put("userID", user.getId());
        this.attributes.put("groupID", group.getId());
    }
}
