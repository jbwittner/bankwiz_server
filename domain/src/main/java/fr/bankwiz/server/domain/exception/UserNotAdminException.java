package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;

public class UserNotAdminException extends FunctionalException {

    public UserNotAdminException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} is not an administrator of group {1}", user.getUserId(), group.getGroupId()));
        this.attributes.put("userID", user.getUserId());
        this.attributes.put("groupID", group.getGroupId());
    }
}
