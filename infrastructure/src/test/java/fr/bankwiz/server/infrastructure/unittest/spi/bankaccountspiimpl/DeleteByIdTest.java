package fr.bankwiz.server.infrastructure.unittest.spi.bankaccountspiimpl;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class DeleteByIdTest extends InfrastructureUnitTestBase {

    private BankAccountSpi bankAccountSpi;

    @Override
    protected void initDataBeforeEach() {
        this.bankAccountSpi = this.buildBankAccountSpiImpl();
    }

    @Test
    void ok() {
        final UUID id = UUID.randomUUID();
        this.bankAccountSpi.deleteById(id);
        this.bankAccountRepositoryMockFactory.verifyDeleteByIdCalled(id, 1);
    }
}
