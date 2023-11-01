package fr.bankwiz.server.infrastructure.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;

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

        final GroupRight groupRight = this.factory.getGroupEntity(GroupRightEnum.ADMIN);

        final GroupRight groupRightSaved = this.groupRightSpiImpl.save(groupRight);

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupRight.getUser(), groupRightSaved.getUser()),
                () -> Assertions.assertEquals(groupRight.getGroup(), groupRightSaved.getGroup()),
                () -> Assertions.assertEquals(groupRight.getGroupRightEnum(), groupRightSaved.getGroupRightEnum()),
                () -> Assertions.assertEquals(groupRight.getGroupRightUuid(), groupRightSaved.getGroupRightUuid()));
    }
}
