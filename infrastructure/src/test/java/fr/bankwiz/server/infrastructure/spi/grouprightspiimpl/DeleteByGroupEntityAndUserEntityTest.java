package fr.bankwiz.server.infrastructure.spi.grouprightspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

class DeleteByGroupEntityAndUserEntityTest extends InfrastructureUnitTestBase {

    private GroupRightSpiImpl groupRightSpiImpl;
    private GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupRightSpiImpl = new GroupRightSpiImpl(groupRightEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void delete() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final Group group = GroupTransformer.fromGroupEntity(groupEntity);

        final UserEntity userEntity = this.factory.getUserEntity();
        final User user = UserTransformer.fromUserEntity(userEntity);

        ArgumentCaptor<GroupEntity> argumentGroupEntity = ArgumentCaptor.forClass(GroupEntity.class);
        ArgumentCaptor<UserEntity> argumentUserEntity = ArgumentCaptor.forClass(UserEntity.class);

        this.groupRightSpiImpl.deleteByGroupEntityAndUserEntity(group, user);

        Mockito.verify(this.groupRightEntityRepositoryMockFactory.getRepository())
                .deleteByGroupEntityAndUserEntity(argumentGroupEntity.capture(), argumentUserEntity.capture());

        Assertions.assertEquals(groupEntity, argumentGroupEntity.getValue());
        Assertions.assertEquals(userEntity, argumentUserEntity.getValue());
    }
}
