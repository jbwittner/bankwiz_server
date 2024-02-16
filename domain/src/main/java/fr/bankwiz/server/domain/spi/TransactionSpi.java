package fr.bankwiz.server.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.TransactionDomain;

public interface TransactionSpi {
    TransactionDomain save(TransactionDomain transaction);

    List<TransactionDomain> findByBankAccount(BankAccountDomain bankAccount);

    Optional<TransactionDomain> findById(UUID uuid);

    void deleteById(UUID uuid);
}
