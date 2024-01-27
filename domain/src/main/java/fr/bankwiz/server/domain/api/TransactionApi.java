package fr.bankwiz.server.domain.api;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;

public interface TransactionApi {
    Transaction createTransaction(TransactionCreationInput transactionCreationInput);

    BankAccountTransactions getAllTransactionOfBankAccount(UUID bankaccountId);
}
