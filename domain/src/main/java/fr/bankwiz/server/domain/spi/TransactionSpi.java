package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.Transaction;

public interface TransactionSpi {
    Transaction save(Transaction transaction);
}
