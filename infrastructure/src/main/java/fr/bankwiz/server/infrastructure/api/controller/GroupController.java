package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.GroupApi;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.infrastructure.service.GroupInfraService;

@Controller
public class GroupController implements GroupApi {

    private GroupInfraService groupInfraService;

    public GroupController(GroupInfraService groupInfraService) {
        this.groupInfraService = groupInfraService;
    }

    @Override
    public ResponseEntity<GroupIndexDTO> createGroup(GroupCreationRequest groupCreationRequest) {
        GroupIndexDTO groupIndexDTO = this.groupInfraService.createGroup(groupCreationRequest);
        return new ResponseEntity<>(groupIndexDTO, HttpStatus.OK);
    }
}
