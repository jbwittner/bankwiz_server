package fr.bankwiz.server.infrastructure.spi.groupspiimpl;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.GroupSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.GroupEntityRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

class FindByIdTest extends InfrastructureUnitTestBase {

    private GroupSpiImpl groupSpiImpl;
    private GroupEntityRepositoryMockFactory groupEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.groupEntityRepositoryMockFactory = new GroupEntityRepositoryMockFactory();
        this.groupSpiImpl = new GroupSpiImpl(groupEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void findGroup() {
        final Group group = this.factory.getGroup();

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        this.groupEntityRepositoryMockFactory.mockFindById(group.getId(), groupEntity);

        final Optional<Group> optionalGroup = this.groupSpiImpl.findById(group.getId());

        Assertions.assertTrue(optionalGroup.isPresent());
        Assertions.assertEquals(group, optionalGroup.get());
    }

    @Test
    void notFindGroup() {
        final UUID uuid = UUID.randomUUID();
        this.groupEntityRepositoryMockFactory.mockFindById(uuid, Optional.empty());

        final Optional<Group> optionalGroup = this.groupSpiImpl.findById(uuid);

        Assertions.assertTrue(optionalGroup.isEmpty());
    }
}
