package fr.bankwiz.server.exception;

public class GroupNotExistException extends FunctionalException {

    public GroupNotExistException(final Integer groupId) {
        super("No group with id : " + groupId);
    }
}
