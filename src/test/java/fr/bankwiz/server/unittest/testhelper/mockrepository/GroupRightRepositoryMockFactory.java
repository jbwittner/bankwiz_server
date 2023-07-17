package fr.bankwiz.server.unittest.testhelper.mockrepository;

import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.repository.GroupRightRepository;

public class GroupRightRepositoryMockFactory
        extends AbstractRepositoryMockFactory<GroupRight, GroupRightRepository, Integer> {
    public GroupRightRepositoryMockFactory() {
        super(GroupRightRepository.class);
    }
}
