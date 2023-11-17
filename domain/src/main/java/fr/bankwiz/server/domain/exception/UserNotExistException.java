package fr.bankwiz.server.domain.exception;

import java.util.UUID;

import lombok.ToString;

@ToString
public class UserNotExistException extends FunctionalException {

    private final String id;

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
        this.id = authId;
    }

    public UserNotExistException(final UUID userAccountId) {
        super("No user with id : " + userAccountId);
        this.id = userAccountId.toString();
    }
}
