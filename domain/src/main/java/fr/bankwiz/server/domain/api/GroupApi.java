package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.input.AddUserGroupInput;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.domain.model.other.GroupDetails;

public interface GroupApi {
    GroupDomain groupCreation(GroupCreationInput groupCreationInput);

    List<GroupDomain> getUserGroups();

    GroupDetails getGroupDetails(UUID groupId);

    GroupRightDomain addUserToGroup(UUID groupId, AddUserGroupInput addUserGroupInput);

    void deleteUserFromGroup(UUID groupId, UUID userId);

    void deleteGroup(UUID groupId);
}
