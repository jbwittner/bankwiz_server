package fr.bankwiz.server.infrastructure.unittest.spi.groupspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class SaveTest extends InfrastructureUnitTestBase {

    private GroupSpi groupSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupSpi = this.buildGroupSpiImpl();
    }

    @Test
    void save() {

        this.groupEntityRepositoryMockFactory.mockSave();

        final Group group = this.factory.getGroup();

        final Group groupSaved = this.groupSpi.save(group);

        final var argumentCaptor = this.groupEntityRepositoryMockFactory.verifySaveCalled(GroupEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(GroupTransformer.toGroupEntity(group), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(group.getGroupName(), groupSaved.getGroupName()),
                () -> Assertions.assertEquals(group.getId(), groupSaved.getId()));
    }
}
