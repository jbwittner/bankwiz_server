package fr.bankwiz.server.unittest.testhelper.mockrepository;

import java.util.List;

import org.mockito.Mockito;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.repository.GroupRightRepository;

public class GroupRightRepositoryMockFactory
        extends AbstractRepositoryMockFactory<GroupRight, GroupRightRepository, Integer> {
    public GroupRightRepositoryMockFactory() {
        super(GroupRightRepository.class);
    }

    public GroupRightRepositoryMockFactory mockFindAllByGroup(Group group, List<GroupRight> groupRights) {
        Mockito.when(repository.findAllByGroup(group)).thenReturn(groupRights);
        return this;
    }
}
