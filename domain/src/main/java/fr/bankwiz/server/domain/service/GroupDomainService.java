package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;

public class GroupDomainService implements GroupApi {

    private final GroupSpi groupSpi;
    private final GroupRightSpi groupRightSpi;
    private final AuthenticationSpi authenticationSpi;

    public GroupDomainService(GroupSpi groupSpi, GroupRightSpi groupRightSpi, AuthenticationSpi authenticationSpi) {
        this.groupSpi = groupSpi;
        this.groupRightSpi = groupRightSpi;
        this.authenticationSpi = authenticationSpi;
    }

    @Override
    public Group groupCreation(GroupCreationInput groupCreationInput) {
        final Group group = Group.builder()
                .groupId(UUID.randomUUID())
                .groupName(groupCreationInput.getGroupName())
                .build();
        final Group groupSaved = this.groupSpi.save(group);
        final User admin = this.authenticationSpi.getCurrentUser();
        final GroupRight groupRight = GroupRight.builder()
                .group(groupSaved)
                .user(admin)
                .groupRightId(UUID.randomUUID())
                .groupRightEnum(GroupRightEnum.ADMIN)
                .build();
        this.groupRightSpi.save(groupRight);
        return groupSaved;
    }

    @Override
    public List<Group> getUserGroups() {
        final User user = this.authenticationSpi.getCurrentUser();
        final List<GroupRight> groupRights = this.groupRightSpi.findByUser(user);
        return groupRights.stream().map(GroupRight::getGroup).toList();
    }
}
