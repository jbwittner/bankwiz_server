package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;

public interface TransactionApi {
    Transaction createTransaction(TransactionCreationInput transactionCreationInput);
}
