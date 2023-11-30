package fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository;

import java.util.UUID;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupEntityRepository;

public class GroupEntityRepositoryMockFactory
        extends AbstractRepositoryMockFactory<GroupEntity, GroupEntityRepository, UUID> {
    public GroupEntityRepositoryMockFactory() {
        super(GroupEntityRepository.class);
    }
}
