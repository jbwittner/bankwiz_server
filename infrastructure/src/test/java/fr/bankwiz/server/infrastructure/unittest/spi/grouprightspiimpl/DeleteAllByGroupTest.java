package fr.bankwiz.server.infrastructure.unittest.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class DeleteAllByGroupTest extends InfrastructureUnitTestBase {

    private GroupRightSpi groupRightSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightSpi = this.buildGroupRightSpiImpl();
    }

    @Test
    void ok() {
        final GroupDomain group = this.factory.getGroup();

        this.groupRightSpi.deleteAllByGroup(group);

        final GroupEntity groupEntityToCheck = GroupTransformer.toGroupEntity(group);

        ArgumentCaptor<GroupEntity> argument = ArgumentCaptor.forClass(GroupEntity.class);
        Mockito.verify(this.groupRightEntityRepositoryMockFactory.getRepository())
                .deleteAllByGroupEntity(argument.capture());
        Assertions.assertEquals(groupEntityToCheck, argument.getValue());
    }
}
