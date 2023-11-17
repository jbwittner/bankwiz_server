package fr.bankwiz.server.domain.exception;

import java.util.UUID;

import lombok.ToString;

@ToString
public class GroupNotExistException extends FunctionalException {

    private final UUID groupId;

    public GroupNotExistException(final UUID id) {
        super("No group with id : " + id);
        this.groupId = id;
    }
}
