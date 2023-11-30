package fr.bankwiz.server.infrastructure.unittest.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;

class DeleteAllByGroupTest extends InfrastructureUnitTestBase {

    private GroupRightSpiImpl groupRightSpiImpl;
    private GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupRightSpiImpl = new GroupRightSpiImpl(groupRightEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void ok() {
        final Group group = this.factory.getGroup();

        this.groupRightSpiImpl.deleteAllByGroup(group);

        final GroupEntity groupEntityToCheck = GroupTransformer.toGroupEntity(group);

        ArgumentCaptor<GroupEntity> argument = ArgumentCaptor.forClass(GroupEntity.class);
        Mockito.verify(this.groupRightEntityRepositoryMockFactory.getRepository())
                .deleteAllByGroupEntity(argument.capture());
        Assertions.assertEquals(groupEntityToCheck, argument.getValue());
    }
}
