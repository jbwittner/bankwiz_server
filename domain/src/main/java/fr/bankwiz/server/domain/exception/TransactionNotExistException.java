package fr.bankwiz.server.domain.exception;

import java.util.UUID;

public class TransactionNotExistException extends FunctionalException {

    public TransactionNotExistException(final UUID id) {
        super("No transaction with id : " + id);
        this.attributes.put("id", id);
    }
}
