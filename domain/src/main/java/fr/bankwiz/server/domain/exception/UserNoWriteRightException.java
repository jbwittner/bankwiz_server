package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;

public class UserNoWriteRightException extends FunctionalException {

    public UserNoWriteRightException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} have no write right of group {1}", user.getUserId(), group.getGroupId()));
    }
}
