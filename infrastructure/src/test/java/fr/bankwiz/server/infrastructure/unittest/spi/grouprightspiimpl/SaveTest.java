package fr.bankwiz.server.infrastructure.unittest.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;

class SaveTest extends InfrastructureUnitTestBase {

    private GroupRightSpiImpl groupRightSpiImpl;
    private GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupRightSpiImpl = new GroupRightSpiImpl(groupRightEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void save() {

        this.groupRightEntityRepositoryMockFactory.mockSave();

        final GroupRight groupRight = this.factory.getGroupRight(GroupRightEnum.ADMIN);

        final GroupRight groupRightSaved = this.groupRightSpiImpl.save(groupRight);

        final var argumentCaptor = this.groupRightEntityRepositoryMockFactory.verifySaveCalled(GroupRightEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        GroupRightTransformer.toGroupRightEntity(groupRight), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(groupRight.getUser(), groupRightSaved.getUser()),
                () -> Assertions.assertEquals(groupRight.getGroup(), groupRightSaved.getGroup()),
                () -> Assertions.assertEquals(groupRight.getGroupRightEnum(), groupRightSaved.getGroupRightEnum()),
                () -> Assertions.assertEquals(groupRight.getId(), groupRightSaved.getId()));
    }
}
