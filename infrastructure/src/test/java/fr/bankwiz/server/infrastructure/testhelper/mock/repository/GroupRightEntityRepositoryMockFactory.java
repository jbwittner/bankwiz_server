package fr.bankwiz.server.infrastructure.testhelper.mock.repository;

import java.util.UUID;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;

public class GroupRightEntityRepositoryMockFactory
        extends AbstractRepositoryMockFactory<GroupRightEntity, GroupRightEntityRepository, UUID> {
    public GroupRightEntityRepositoryMockFactory() {
        super(GroupRightEntityRepository.class);
    }
}
