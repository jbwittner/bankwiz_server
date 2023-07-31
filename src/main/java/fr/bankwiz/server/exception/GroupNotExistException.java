package fr.bankwiz.server.exception;

public class GroupNotExistException extends FunctionalException {

    public GroupNotExistException(final Integer userAccountId) {
        super("No group with id : " + userAccountId);
    }
}
