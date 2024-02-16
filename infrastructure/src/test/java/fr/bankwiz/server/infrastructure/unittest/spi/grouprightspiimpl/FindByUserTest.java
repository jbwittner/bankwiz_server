package fr.bankwiz.server.infrastructure.unittest.spi.grouprightspiimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByUserTest extends InfrastructureUnitTestBase {

    private GroupRightSpi groupRightSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightSpi = this.buildGroupRightSpiImpl();
    }

    @Test
    void listWithData() {
        final UserEntity userEntity = this.factory.getUserEntity();
        final UserDomain user = UserTransformer.fromUserEntity(userEntity);

        final GroupRightEntity groupRightEntity1 =
                this.factory.getGroupRightEntity(userEntity, GroupRightEntityEnum.ADMIN);
        final GroupRightEntity groupRightEntity2 =
                this.factory.getGroupRightEntity(userEntity, GroupRightEntityEnum.READ);
        final List<GroupRightEntity> groupRightEntities = new ArrayList<>();
        groupRightEntities.add(groupRightEntity1);
        groupRightEntities.add(groupRightEntity2);

        this.groupRightEntityRepositoryMockFactory.mockFindByUserEntity(groupRightEntities);

        final List<GroupRightDomain> groupRights = this.groupRightSpi.findByUser(user);

        this.groupRightEntityRepositoryMockFactory.verifyFindByUserEntity(userEntity);

        Assertions.assertEquals(groupRightEntities.size(), groupRights.size());

        Assertions.assertEquals(
                groupRightEntity1.getGroupEntity().getId(),
                groupRights.get(0).getGroup().getId());
        Assertions.assertEquals(
                groupRightEntity2.getGroupEntity().getId(),
                groupRights.get(1).getGroup().getId());

        Assertions.assertEquals(
                groupRightEntity1.getUserEntity().getId(),
                groupRights.get(0).getUser().getId());
        Assertions.assertEquals(
                groupRightEntity2.getUserEntity().getId(),
                groupRights.get(1).getUser().getId());
    }
}
