package fr.bankwiz.server.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.GroupRepository;
import fr.bankwiz.server.repository.GroupRightRepository;
import fr.bankwiz.server.repository.UserRepository;

@Component
public class IntegrationTestFactory extends TestFactory {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupRightRepository groupRightRepository;

    public User getUser() {
        final User user = super.getUser();
        return this.userRepository.save(user);
    }

    public Group getGroup() {
        Group group = super.getGroup();
        return this.groupRepository.save(group);
    }

    public void addUserToGroup(Group group, User user, GroupRightEnum groupRightEnum) {
        GroupRight groupRight = super.getGroupRight(user, group, groupRightEnum);
        this.groupRightRepository.save(groupRight);
    }

    public Group getGroupWithRight(User userToAdd, GroupRightEnum groupRightEnum) {
        final Group group = this.getGroup();
        this.addUserToGroup(group, userToAdd, groupRightEnum);
        return group;
    }
}
