package fr.bankwiz.server.infrastructure.unittest.spi.bankaccountspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ExistsByGroupTest extends InfrastructureUnitTestBase {

    private BankAccountSpi bankAccountSpi;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountSpi = this.buildBankAccountSpiImpl();
    }

    @Test
    void bankAccountExist() {

        final Group group = this.factory.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        this.bankAccountRepositoryMockFactory.mockExistByGroupEntity(groupEntity, true);

        final boolean exist = this.bankAccountSpi.existsByGroup(group);

        Assertions.assertTrue(exist);
    }

    @Test
    void bankAccountNotExist() {

        final Group group = this.factory.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        this.bankAccountRepositoryMockFactory.mockExistByGroupEntity(groupEntity, false);

        final boolean exist = this.bankAccountSpi.existsByGroup(group);

        Assertions.assertFalse(exist);
    }
}
