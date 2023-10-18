package fr.bankwiz.server.domain.exception;

public class UserNotExistException extends FunctionalException {

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
    }

    public UserNotExistException(final Integer userAccountId) {
        super("No user with id : " + userAccountId);
    }
}