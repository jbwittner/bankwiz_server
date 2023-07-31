package fr.bankwiz.server.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;

public class UserNotAdminException extends FunctionalException {

    public UserNotAdminException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} {1} is not an administrator of group {2}",
                user.getFirstName(), user.getLastName(), group.getName()));
    }
}
