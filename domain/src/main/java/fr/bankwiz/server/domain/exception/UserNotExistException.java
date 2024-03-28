package fr.bankwiz.server.domain.exception;

public class UserNotExistException extends FunctionalException {

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
        this.attributes.put("id", authId);
    }
}
