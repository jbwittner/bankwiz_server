package fr.bankwiz.server.domain.exception;

import java.util.UUID;

import lombok.ToString;

@ToString
public class UserNotExistException extends FunctionalException {

    private final String authID;
    private final UUID userAccountId;

    public UserNotExistException(final String authId) {
        super("No user with authId : " + authId);
        this.authID = authId;
        this.userAccountId = null;
    }

    public UserNotExistException(final UUID userAccountId) {
        super("No user with id : " + userAccountId);
        this.authID = null;
        this.userAccountId = userAccountId;
    }
}
