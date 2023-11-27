package fr.bankwiz.server.infrastructure.spi.groupspiimpl;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

class FindByIdTest extends InfrastructureUnitTestBase {

    private GroupSpi groupSpi;

    @Override
    protected void initDataBeforeEach() {
        this.groupSpi = this.buildGroupSpiImpl();
    }

    @Test
    void findGroup() {
        final Group group = this.factory.getGroup();

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        this.groupEntityRepositoryMockFactory.mockFindById(group.getId(), groupEntity);

        final Optional<Group> optionalGroup = this.groupSpi.findById(group.getId());

        Assertions.assertTrue(optionalGroup.isPresent());
        Assertions.assertEquals(group, optionalGroup.get());
    }

    @Test
    void notFindGroup() {
        final UUID uuid = UUID.randomUUID();
        this.groupEntityRepositoryMockFactory.mockFindById(uuid, Optional.empty());

        final Optional<Group> optionalGroup = this.groupSpi.findById(uuid);

        Assertions.assertTrue(optionalGroup.isEmpty());
    }
}
