package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

@Service
public class GroupInfraService {

    private final GroupApi groupApi;

    public GroupInfraService(GroupApi groupApi) {
        this.groupApi = groupApi;
    }

    public GroupIndexDTO createGroup(GroupCreationInput groupCreationInput) {

        final Group group = this.groupApi.groupCreation(groupCreationInput);
        return GroupTransformer.toGroupIndexDTO(group);
    }
}
