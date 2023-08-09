package fr.bankwiz.server.exception;

public class TransactionNotExistException extends FunctionalException {
    public TransactionNotExistException(final Integer transactionId) {
        super("No transaction with id : " + transactionId);
    }
}
