package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;

public class UserNoAccessGroupException extends FunctionalException {

    public UserNoAccessGroupException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} does not have access to group {1}", user.getUserId(), group.getGroupId()));
        this.attributes.put("userID", user.getUserId());
        this.attributes.put("groupID", group.getGroupId());
    }
}
