package fr.bankwiz.server.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;

public interface BankAccountSpi {

    BankAccount save(BankAccount bankAccount);

    boolean existsByGroup(Group group);

    List<BankAccount> findByGroup(Group group);

    Optional<BankAccount> findById(UUID id);

    default BankAccount getById(UUID id) {
        return this.findById(id).orElseThrow(() -> new BankAccountNotExistException(id));
    }

    void deleteById(UUID id);
}
