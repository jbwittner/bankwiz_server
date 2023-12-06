package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

@Component
public class BankAccountSpiImpl implements BankAccountSpi {

    private BankAccountEntityRepository bankAccountEntityRepository;

    public BankAccountSpiImpl(BankAccountEntityRepository bankAccountEntityRepository) {
        this.bankAccountEntityRepository = bankAccountEntityRepository;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);
        final BankAccountEntity bankAccountEntitySaved = this.bankAccountEntityRepository.save(bankAccountEntity);
        return BankAccountTransformer.fromBankAccountEntity(bankAccountEntitySaved);
    }

    @Override
    public boolean existsByGroup(Group group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        return this.bankAccountEntityRepository.existsByGroupEntity(groupEntity);
    }
}
