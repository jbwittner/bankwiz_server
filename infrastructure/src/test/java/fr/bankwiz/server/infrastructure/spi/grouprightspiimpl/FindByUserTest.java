package fr.bankwiz.server.infrastructure.spi.grouprightspiimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;

class FindByUserTest extends InfrastructureUnitTestBase {

    private GroupRightSpiImpl groupRightSpiImpl;
    private GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupRightSpiImpl = new GroupRightSpiImpl(groupRightEntityRepositoryMockFactory.getRepository());
    }


    @Test
    void listWithData() {
        final UserEntity userEntity = this.factory.getUserEntity();
        final GroupRightEntity groupRightEntity1 = this.factory.getGroupRightEntity(userEntity, GroupRightEntityEnum.ADMIN);
        final GroupRightEntity groupRightEntity2 = this.factory.getGroupRightEntity(userEntity, GroupRightEntityEnum.READ);
        final List<GroupRightEntity> groupRightEntities = new ArrayList<>();
        groupRightEntities.add(groupRightEntity1);
        groupRightEntities.add(groupRightEntity2);

    }

    
}
