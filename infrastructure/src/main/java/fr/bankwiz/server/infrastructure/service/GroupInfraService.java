package fr.bankwiz.server.infrastructure.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.input.AddUserGroupInputDomain;
import fr.bankwiz.server.domain.model.input.GroupCreationInputDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;

@Service
public class GroupInfraService {

    private final GroupApi groupApi;

    public GroupInfraService(GroupApi groupApi) {
        this.groupApi = groupApi;
    }

    @Transactional
    public GroupDomain createGroup(GroupCreationInputDomain groupCreationInput) {
        return this.groupApi.groupCreation(groupCreationInput);
    }

    @Transactional(readOnly = true)
    public List<GroupDomain> getUserGroups() {
        return this.groupApi.getUserGroups();
    }

    @Transactional(readOnly = true)
    public GroupDetailsDomain getGroupDetails(final UUID id) {
        return this.groupApi.getGroupDetails(id);
    }

    @Transactional
    public GroupRightDomain addUserGroup(UUID groupId, AddUserGroupInputDomain addUserGroupInput) {
        return this.groupApi.addUserToGroup(groupId, addUserGroupInput);
    }

    @Transactional
    public void deleteUserFromGroup(UUID groupId, UUID userId) {
        this.groupApi.deleteUserFromGroup(groupId, userId);
    }

    @Transactional
    public void deleteGroup(UUID groupId) {
        this.groupApi.deleteGroup(groupId);
    }
}
