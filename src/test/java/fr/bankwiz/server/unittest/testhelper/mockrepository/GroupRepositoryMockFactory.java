package fr.bankwiz.server.unittest.testhelper.mockrepository;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.repository.GroupRepository;

public class GroupRepositoryMockFactory extends AbstractRepositoryMockFactory<Group, GroupRepository, Integer> {
    public GroupRepositoryMockFactory() {
        super(GroupRepository.class);
    }
}
