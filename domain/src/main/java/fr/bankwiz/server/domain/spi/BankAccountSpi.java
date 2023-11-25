package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.BankAccount;

public interface BankAccountSpi {

    BankAccount save(BankAccount bankAccount);
}
