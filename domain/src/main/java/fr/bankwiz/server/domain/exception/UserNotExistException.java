package fr.bankwiz.server.domain.exception;

import java.util.UUID;

public class UserNotExistException extends FunctionalException {

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
    }

    public UserNotExistException(final UUID userAccountId) {
        super("No user with id : " + userAccountId);
    }
}
