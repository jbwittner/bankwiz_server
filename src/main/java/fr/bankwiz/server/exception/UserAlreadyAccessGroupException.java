package fr.bankwiz.server.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;

public class UserAlreadyAccessGroupException extends FunctionalException {

    public UserAlreadyAccessGroupException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} {1} have already access of the group {2}",
                user.getFirstName(), user.getLastName(), group.getName()));
    }
}
