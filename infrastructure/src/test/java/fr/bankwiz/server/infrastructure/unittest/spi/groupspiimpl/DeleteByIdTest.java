package fr.bankwiz.server.infrastructure.unittest.spi.groupspiimpl;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class DeleteByIdTest extends InfrastructureUnitTestBase {

    private GroupSpi groupSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupSpi = this.buildGroupSpiImpl();
    }

    @Test
    void ok() {
        final UUID id = UUID.randomUUID();
        this.groupSpi.deleteById(id);
        this.groupEntityRepositoryMockFactory.verifyDeleteByIdCalled(id, 1);
    }
}
