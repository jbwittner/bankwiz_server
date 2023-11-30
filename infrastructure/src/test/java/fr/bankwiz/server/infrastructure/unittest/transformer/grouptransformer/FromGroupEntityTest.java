package fr.bankwiz.server.infrastructure.unittest.transformer.grouptransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FromGroupEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final Group group = GroupTransformer.fromGroupEntity(groupEntity);
        Assertions.assertAll(
                () -> Assertions.assertEquals(groupEntity.getGroupName(), group.getGroupName()),
                () -> Assertions.assertEquals(groupEntity.getId(), group.getId()));
    }
}
