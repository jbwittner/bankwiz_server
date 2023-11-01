package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.GroupApi;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
import fr.bankwiz.server.infrastructure.service.GroupInfraService;

@Controller
public class GroupController implements GroupApi {

    private GroupInfraService groupInfraService;

    public GroupController(GroupInfraService groupInfraService) {
        this.groupInfraService = groupInfraService;
    }

    @Override
    public ResponseEntity<GroupIndexDTO> createGroup(GroupCreationRequest groupCreationRequest) {
        final GroupCreationInput groupCreationInput = GroupCreationInput.builder()
                .groupName(groupCreationRequest.getGroupName())
                .build();
        GroupIndexDTO groupIndexDTO = this.groupInfraService.createGroup(groupCreationInput);
        return new ResponseEntity<>(groupIndexDTO, HttpStatus.OK);
    }
}
