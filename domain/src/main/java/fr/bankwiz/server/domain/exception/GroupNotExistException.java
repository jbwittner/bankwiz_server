package fr.bankwiz.server.domain.exception;

import java.util.UUID;

public class GroupNotExistException extends FunctionalException {

    public GroupNotExistException(final UUID id) {
        super("No group with id : " + id);
    }

}