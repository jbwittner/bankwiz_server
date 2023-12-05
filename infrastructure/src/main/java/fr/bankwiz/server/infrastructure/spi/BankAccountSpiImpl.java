package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;

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
}
