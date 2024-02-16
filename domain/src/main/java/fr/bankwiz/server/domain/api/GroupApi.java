package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.input.AddUserGroupInputDomain;
import fr.bankwiz.server.domain.model.input.GroupCreationInputDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;

public interface GroupApi {
    GroupDomain groupCreation(GroupCreationInputDomain groupCreationInput);

    List<GroupDomain> getUserGroups();

    GroupDetailsDomain getGroupDetails(UUID groupId);

    GroupRightDomain addUserToGroup(UUID groupId, AddUserGroupInputDomain addUserGroupInput);

    void deleteUserFromGroup(UUID groupId, UUID userId);

    void deleteGroup(UUID groupId);
}
