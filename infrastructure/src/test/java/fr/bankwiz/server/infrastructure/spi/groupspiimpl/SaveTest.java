package fr.bankwiz.server.infrastructure.spi.groupspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.GroupSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupEntityRepositoryMockFactory;

class SaveTest extends InfrastructureUnitTestBase {

    private GroupSpiImpl groupSpiImpl;
    private GroupEntityRepositoryMockFactory groupEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupEntityRepositoryMockFactory = new GroupEntityRepositoryMockFactory();
        this.groupSpiImpl = new GroupSpiImpl(groupEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void save() {

        this.groupEntityRepositoryMockFactory.mockSave();

        final Group group = this.factory.getGroup();

        final Group groupSaved = this.groupSpiImpl.save(group);

        Assertions.assertAll(
                () -> Assertions.assertEquals(group.getGroupName(), groupSaved.getGroupName()),
                () -> Assertions.assertEquals(group.getGroupId(), groupSaved.getGroupId()));
    }
}
