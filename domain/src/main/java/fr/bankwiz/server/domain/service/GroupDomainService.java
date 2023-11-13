package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupDetails;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class GroupDomainService implements GroupApi {

    private final GroupSpi groupSpi;
    private final GroupRightSpi groupRightSpi;
    private final AuthenticationSpi authenticationSpi;
    private final CheckRightTools checkRightTools;

    public GroupDomainService(GroupSpi groupSpi, GroupRightSpi groupRightSpi, AuthenticationSpi authenticationSpi, CheckRightTools checkRightTools) {
        this.groupSpi = groupSpi;
        this.groupRightSpi = groupRightSpi;
        this.authenticationSpi = authenticationSpi;
        this.checkRightTools = checkRightTools;
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

    @Override
    public GroupDetails getGroupDetails(UUID groupId) {
        final Optional<Group> optionalGroup = this.groupSpi.findById(groupId);
        final Group group = optionalGroup.orElseThrow(() -> new GroupNotExistException(groupId));
        this.checkRightTools.checkCanRead(this.authenticationSpi.getCurrentUser(), group);
        final List<GroupRight> groupRights = this.groupRightSpi.findByGroup(group);
        return GroupDetails.builder().group(group).groupRights(groupRights).build();
    }
}
