package fr.bankwiz.server.domain.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;

public interface BankAccountSpi {

    BankAccountDomain save(BankAccountDomain bankAccount);

    boolean existsByGroup(GroupDomain group);

    List<BankAccountDomain> findByGroup(GroupDomain group);

    Optional<BankAccountDomain> findById(UUID id);

    default BankAccountDomain getById(UUID id) {
        return this.findById(id).orElseThrow(() -> new BankAccountNotExistException(id));
    }

    void deleteById(UUID id);
}
