package fr.bankwiz.server.infrastructure.spi.grouprightspiimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

class FindByGroupTest extends InfrastructureUnitTestBase {

    private GroupRightSpiImpl groupRightSpiImpl;
    private GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupRightSpiImpl = new GroupRightSpiImpl(groupRightEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void listWithData() {
        final GroupEntity groupEntity = this.factory.getGroupEntity();
        final Group group = GroupTransformer.fromGroupEntity(groupEntity);

        final GroupRightEntity groupRightEntity1 =
                this.factory.getGroupRightEntity(groupEntity, GroupRightEntityEnum.ADMIN);
        final GroupRightEntity groupRightEntity2 =
                this.factory.getGroupRightEntity(groupEntity, GroupRightEntityEnum.READ);
        final List<GroupRightEntity> groupRightEntities = new ArrayList<>();
        groupRightEntities.add(groupRightEntity1);
        groupRightEntities.add(groupRightEntity2);

        this.groupRightEntityRepositoryMockFactory.mockFindByGroupEntity(groupRightEntities);

        final List<GroupRight> groupRights = this.groupRightSpiImpl.findByGroup(group);

        this.groupRightEntityRepositoryMockFactory.verifyFindByGroupEntity(groupEntity);

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
