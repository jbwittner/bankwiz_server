package fr.bankwiz.server.exception;

public class UserNotExistException extends FunctionalException {

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
    }

    public UserNotExistException(final Integer userId) {
        super("No user with id : " + userId);
    }
}
