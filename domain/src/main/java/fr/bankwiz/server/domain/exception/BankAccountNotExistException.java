package fr.bankwiz.server.domain.exception;

import java.util.UUID;

public class BankAccountNotExistException extends FunctionalException {

    public BankAccountNotExistException(final UUID id) {
        super("No bank account with id : " + id);
        this.attributes.put("id", id);
    }
}
