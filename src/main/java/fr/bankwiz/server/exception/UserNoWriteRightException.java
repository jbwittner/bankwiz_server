package fr.bankwiz.server.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;

public class UserNoWriteRightException extends FunctionalException {

    public UserNoWriteRightException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} {1} have no write right of group {2}",
                user.getFirstName(), user.getLastName(), group.getName()));
    }
}
