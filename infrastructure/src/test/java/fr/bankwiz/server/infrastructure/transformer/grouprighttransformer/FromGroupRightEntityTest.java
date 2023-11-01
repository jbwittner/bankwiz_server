package fr.bankwiz.server.infrastructure.transformer.grouprighttransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;

class FromGroupRightEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final GroupRightEntity groupRightEntity = this.factory.getGroupRightEntity(GroupRightEntityEnum.WRITE);
        final GroupRight groupRight = GroupRightTransformer.fromGroupRightEntity(groupRightEntity);
        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        groupRightEntity.getGroupRightEntityEnum().name(),
                        groupRight.getGroupRightEnum().name()),
                () -> {
                    final UserEntity userEntity = groupRightEntity.getUserEntity();
                    final User user = groupRight.getUser();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(userEntity.getEmail(), user.getEmail()),
                            () -> Assertions.assertEquals(userEntity.getAuthId(), user.getAuthId()),
                            () -> Assertions.assertEquals(userEntity.getUserId(), user.getUserUuid()));
                },
                () -> {
                    final GroupEntity groupEntity = groupRightEntity.getGroupEntity();
                    final Group group = groupRight.getGroup();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(groupEntity.getGroupName(), group.getGroupName()),
                            () -> Assertions.assertEquals(groupEntity.getGroupId(), group.getGroupUuid()));
                });
    }
}
