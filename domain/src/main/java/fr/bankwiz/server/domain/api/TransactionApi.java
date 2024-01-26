package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;

public interface TransactionApi {
    Transaction createTransaction(TransactionCreationInput transactionCreationInput);

    List<Transaction> getAllTransactionOfBankAccount(UUID bankaccountId);
}
