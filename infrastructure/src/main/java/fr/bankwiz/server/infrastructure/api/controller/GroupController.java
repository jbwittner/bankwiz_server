package fr.bankwiz.server.infrastructure.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.GroupApi;
import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.input.AddUserGroupInputDomain;
import fr.bankwiz.server.domain.model.input.GroupCreationInputDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;
import fr.bankwiz.server.infrastructure.service.GroupInfraService;
import fr.bankwiz.server.infrastructure.transformer.GroupDetailsTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

@Controller
public class GroupController implements GroupApi {

    private GroupInfraService groupInfraService;

    public GroupController(GroupInfraService groupInfraService) {
        this.groupInfraService = groupInfraService;
    }

    @Override
    public ResponseEntity<GroupIndexDTO> createGroup(GroupCreationRequest groupCreationRequest) {
        final GroupCreationInputDomain groupCreationInput = GroupCreationInputDomain.builder()
                .groupName(groupCreationRequest.getGroupName())
                .build();
        final GroupDomain group = this.groupInfraService.createGroup(groupCreationInput);
        final GroupIndexDTO groupIndexDTO = GroupTransformer.toGroupIndexDTO(group);
        return new ResponseEntity<>(groupIndexDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GroupIndexDTO>> getUserGroups() {
        final List<GroupDomain> groups = this.groupInfraService.getUserGroups();
        final List<GroupIndexDTO> groupIndexDTOs = GroupTransformer.toGroupIndexDTO(groups);
        return new ResponseEntity<>(groupIndexDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDetailsDTO> getGroupDetails(UUID id) {
        final GroupDetailsDomain groupDetails = this.groupInfraService.getGroupDetails(id);
        final GroupDetailsDTO groupDetailsDTO = GroupDetailsTransformer.toGroupDetailsDTO(groupDetails);
        return new ResponseEntity<>(groupDetailsDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserGroupRightDTO> addUserGroup(UUID groupId, AddUserGroupRequest addUserGroupRequest) {
        final AddUserGroupInputDomain addUserGroupInput = AddUserGroupInputDomain.builder()
                .userId(addUserGroupRequest.getUserId())
                .right(GroupRightEnum.valueOf(addUserGroupRequest.getRight().name()))
                .build();
        final GroupRightDomain groupRight = this.groupInfraService.addUserGroup(groupId, addUserGroupInput);
        final UserGroupRightDTO userGroupRightDTO = GroupRightTransformer.toGroupRightDTO(groupRight);
        return new ResponseEntity<>(userGroupRightDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUserFromGroup(UUID groupId, UUID userId) {
        this.groupInfraService.deleteUserFromGroup(groupId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(UUID id) {
        this.groupInfraService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
