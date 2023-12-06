package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.input.AddUserGroupInput;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.domain.model.other.GroupDetails;

public interface GroupApi {
    Group groupCreation(GroupCreationInput groupCreationInput);

    List<Group> getUserGroups();

    GroupDetails getGroupDetails(UUID groupId);

    GroupRight addUserToGroup(UUID groupId, AddUserGroupInput addUserGroupInput);

    void deleteUserFromGroup(UUID groupId, UUID userId);

    void deleteGroup(UUID groupId);
}
