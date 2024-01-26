package fr.bankwiz.server.domain.spi;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;

public interface TransactionSpi {
    Transaction save(Transaction transaction);

    List<Transaction> findByBankAccount(BankAccount bankAccount);
}
