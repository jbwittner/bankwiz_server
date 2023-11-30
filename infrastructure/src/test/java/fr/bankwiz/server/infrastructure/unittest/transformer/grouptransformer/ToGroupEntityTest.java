package fr.bankwiz.server.infrastructure.unittest.transformer.grouptransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final Group group = this.factory.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        Assertions.assertAll(
                () -> Assertions.assertEquals(group.getGroupName(), groupEntity.getGroupName()),
                () -> Assertions.assertEquals(group.getId(), groupEntity.getId()));
    }
}
