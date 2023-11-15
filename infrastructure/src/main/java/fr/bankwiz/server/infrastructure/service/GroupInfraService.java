package fr.bankwiz.server.infrastructure.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupDetails;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.input.AddUserGroupInput;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;

@Service
public class GroupInfraService {

    private final GroupApi groupApi;

    public GroupInfraService(GroupApi groupApi) {
        this.groupApi = groupApi;
    }

    @Transactional
    public Group createGroup(GroupCreationInput groupCreationInput) {
        return this.groupApi.groupCreation(groupCreationInput);
    }

    @Transactional(readOnly = true)
    public List<Group> getUserGroups() {
        return this.groupApi.getUserGroups();
    }

    @Transactional(readOnly = true)
    public GroupDetails getGroupDetails(final UUID id) {
        return this.groupApi.getGroupDetails(id);
    }

    @Transactional
    public GroupRight addUserGroup(UUID id, AddUserGroupInput addUserGroupInput) {
        return this.groupApi.addUserToGroup(id, addUserGroupInput);
    }
}
