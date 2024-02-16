package fr.bankwiz.server.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Transaction;

public interface TransactionSpi {
    Transaction save(Transaction transaction);

    List<Transaction> findByBankAccount(BankAccountDomain bankAccount);

    Optional<Transaction> findById(UUID uuid);

    void deleteById(UUID uuid);
}
