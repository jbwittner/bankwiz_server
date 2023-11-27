package fr.bankwiz.server.infrastructure.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

class DeleteByGroupAndUserTest extends InfrastructureUnitTestBase {

    private GroupRightSpi groupRightSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightSpi = this.buildGroupRightSpiImpl();
    }

    @Test
    void delete() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final Group group = GroupTransformer.fromGroupEntity(groupEntity);

        final UserEntity userEntity = this.factory.getUserEntity();
        final User user = UserTransformer.fromUserEntity(userEntity);

        ArgumentCaptor<GroupEntity> argumentGroupEntity = ArgumentCaptor.forClass(GroupEntity.class);
        ArgumentCaptor<UserEntity> argumentUserEntity = ArgumentCaptor.forClass(UserEntity.class);

        this.groupRightSpi.deleteByGroupAndUser(group, user);

        Mockito.verify(this.groupRightEntityRepositoryMockFactory.getRepository())
                .deleteByGroupEntityAndUserEntity(argumentGroupEntity.capture(), argumentUserEntity.capture());

        Assertions.assertEquals(groupEntity, argumentGroupEntity.getValue());
        Assertions.assertEquals(userEntity, argumentUserEntity.getValue());
    }
}
