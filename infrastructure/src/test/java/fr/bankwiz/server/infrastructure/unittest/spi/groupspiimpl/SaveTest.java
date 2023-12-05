package fr.bankwiz.server.infrastructure.unittest.spi.groupspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.GroupSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.GroupEntityRepositoryMockFactory;

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

        final var argumentCaptor = this.groupEntityRepositoryMockFactory.verifySaveCalled(GroupEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(GroupTransformer.toGroupEntity(group), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(group.getGroupName(), groupSaved.getGroupName()),
                () -> Assertions.assertEquals(group.getId(), groupSaved.getId()));
    }
}
