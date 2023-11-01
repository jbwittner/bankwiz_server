package fr.bankwiz.server.infrastructure.transformer.grouprighttransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

class FromGroupRightEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final GroupRightEntity groupRightEntity = this.factory.getGroupRightEntity(GroupRightEntityEnum.WRITE);
        final GroupRight groupRight = GroupRightTransformer.fromGroupRightEntity(groupRightEntity);
        Assertions.assertAll(
                () -> Assertions.assertEquals(group.getGroupName(), groupEntity.getGroupName()),
                () -> Assertions.assertEquals(group.getGroupUuid(), groupEntity.getGroupId()));
    }
}
