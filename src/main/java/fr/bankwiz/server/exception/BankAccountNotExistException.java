package fr.bankwiz.server.exception;

public class BankAccountNotExistException extends FunctionalException {

    public BankAccountNotExistException(final Integer bankAccountId) {
        super("No bank account with id : " + bankAccountId);
    }
}
