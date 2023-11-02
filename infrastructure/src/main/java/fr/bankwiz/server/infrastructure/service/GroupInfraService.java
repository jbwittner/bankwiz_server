package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.Group;
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
}
