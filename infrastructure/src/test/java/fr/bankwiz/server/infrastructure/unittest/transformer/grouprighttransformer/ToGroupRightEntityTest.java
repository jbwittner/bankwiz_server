package fr.bankwiz.server.infrastructure.unittest.transformer.grouprighttransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupRightEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final GroupRight groupRight = this.factory.getGroupRight(GroupRightEnum.WRITE);
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        groupRightEntity.getGroupRightEntityEnum().name(),
                        groupRight.getGroupRightEnum().name()),
                () -> {
                    final UserEntity userEntity = groupRightEntity.getUserEntity();
                    final User user = groupRight.getUser();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(user.getEmail(), userEntity.getEmail()),
                            () -> Assertions.assertEquals(user.getAuthId(), userEntity.getAuthId()),
                            () -> Assertions.assertEquals(user.getId(), userEntity.getId()));
                },
                () -> {
                    final GroupEntity groupEntity = groupRightEntity.getGroupEntity();
                    final Group group = groupRight.getGroup();

                    Assertions.assertAll(
                            () -> Assertions.assertEquals(group.getGroupName(), groupEntity.getGroupName()),
                            () -> Assertions.assertEquals(group.getId(), groupEntity.getId()));
                });
    }
}
