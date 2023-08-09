package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.TransactionIndexDTO;
import fr.bankwiz.server.model.Transaction;

public class TransactionIndexDTOBuilder implements Transformer<Transaction, TransactionIndexDTO> {

    public TransactionIndexDTO transform(final Transaction input) {
        return new TransactionIndexDTO(input.getTransactionId(), input.getAmount(), input.getDate());
    }
}
