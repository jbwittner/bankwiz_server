package fr.bankwiz.server.infrastructure.spi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
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
    public BankAccountDomain save(BankAccountDomain bankAccount) {
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);
        final BankAccountEntity bankAccountEntitySaved = this.bankAccountEntityRepository.save(bankAccountEntity);
        return BankAccountTransformer.fromBankAccountEntity(bankAccountEntitySaved);
    }

    @Override
    public boolean existsByGroup(GroupDomain group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        return this.bankAccountEntityRepository.existsByGroupEntity(groupEntity);
    }

    @Override
    public List<BankAccountDomain> findByGroup(GroupDomain group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        List<BankAccountEntity> bankAccountEntities = this.bankAccountEntityRepository.findByGroupEntity(groupEntity);
        return BankAccountTransformer.fromBankAccountEntity(bankAccountEntities);
    }

    @Override
    public Optional<BankAccountDomain> findById(UUID id) {
        final Optional<BankAccountEntity> optionalBankAccountEntity = this.bankAccountEntityRepository.findById(id);

        if (optionalBankAccountEntity.isEmpty()) {
            return Optional.empty();
        } else {
            final BankAccountDomain bankAccount =
                    BankAccountTransformer.fromBankAccountEntity(optionalBankAccountEntity.get());
            return Optional.of(bankAccount);
        }
    }

    @Override
    public void deleteById(UUID id) {
        this.bankAccountEntityRepository.deleteById(id);
    }
}
