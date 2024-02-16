package fr.bankwiz.server.infrastructure.unittest.spi.bankaccountspiimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByGroupTest extends InfrastructureUnitTestBase {

    private BankAccountSpi bankAccountSpi;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountSpi = this.buildBankAccountSpiImpl();
    }

    @Test
    void ok() {

        final GroupDomain group = this.factory.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        List<BankAccountEntity> bankAccountEntities = new ArrayList<>();
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));
        bankAccountEntities.add(this.factory.getBankAccountEntity(groupEntity));

        this.bankAccountRepositoryMockFactory.mockFindByGroupEntity(groupEntity, bankAccountEntities);

        final List<BankAccountDomain> result = this.bankAccountSpi.findByGroup(group);

        result.forEach(bankAccount -> {
            final BankAccountEntity entity = bankAccountEntities.stream()
                    .filter(bankAccountEntity -> bankAccountEntity.getId().equals(bankAccount.getId()))
                    .findFirst()
                    .orElseThrow();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(entity.getBaseAmountDecimal(), bankAccount.getDecimalBaseAmount()),
                    () -> Assertions.assertEquals(entity.getBankAccountName(), bankAccount.getBankAccountName()),
                    () -> Assertions.assertEquals(group, bankAccount.getGroup()));
        });
    }
}
