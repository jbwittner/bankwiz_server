package fr.bankwiz.server.domain.spi;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;

public interface BankAccountSpi {

    BankAccount save(BankAccount bankAccount);

    boolean existsByGroup(Group group);

    List<BankAccount> findByGroup(Group group);
}
