package fr.bankwiz.server.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;

public class UserNoAccessGroupException extends FunctionalException {

    public UserNoAccessGroupException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} {1} does not have access to group {2}",
                user.getFirstName(), user.getLastName(), group.getName()));
    }
}
