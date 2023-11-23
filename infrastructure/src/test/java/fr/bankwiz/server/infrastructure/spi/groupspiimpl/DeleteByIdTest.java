package fr.bankwiz.server.infrastructure.spi.groupspiimpl;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.infrastructure.spi.GroupSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupEntityRepositoryMockFactory;

class DeleteByIdTest extends InfrastructureUnitTestBase {

    private GroupSpiImpl groupSpiImpl;
    private GroupEntityRepositoryMockFactory groupEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupEntityRepositoryMockFactory = new GroupEntityRepositoryMockFactory();
        this.groupSpiImpl = new GroupSpiImpl(groupEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void ok() {
        final UUID id = UUID.randomUUID();
        this.groupSpiImpl.deleteById(id);
        this.groupEntityRepositoryMockFactory.verifyDeleteByIdCalled(id, 1);
    }
}
