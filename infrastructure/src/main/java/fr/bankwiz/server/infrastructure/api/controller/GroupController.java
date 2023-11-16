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
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupDetails;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.input.AddUserGroupInput;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;
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
        final GroupCreationInput groupCreationInput = GroupCreationInput.builder()
                .groupName(groupCreationRequest.getGroupName())
                .build();
        final Group group = this.groupInfraService.createGroup(groupCreationInput);
        final GroupIndexDTO groupIndexDTO = GroupTransformer.toGroupIndexDTO(group);
        return new ResponseEntity<>(groupIndexDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GroupIndexDTO>> getUserGroups() {
        final List<Group> groups = this.groupInfraService.getUserGroups();
        final List<GroupIndexDTO> groupIndexDTOs = GroupTransformer.toGroupIndexDTO(groups);
        return new ResponseEntity<>(groupIndexDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDetailsDTO> getGroupDetails(UUID id) {
        final GroupDetails groupDetails = this.groupInfraService.getGroupDetails(id);
        final GroupDetailsDTO groupDetailsDTO = GroupDetailsTransformer.toGroupDetailsDTO(groupDetails);
        return new ResponseEntity<>(groupDetailsDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserGroupRightDTO> addUserGroup(UUID groupId, AddUserGroupRequest addUserGroupRequest) {
        final AddUserGroupInput addUserGroupInput = AddUserGroupInput.builder()
                .userId(addUserGroupRequest.getUserId())
                .right(GroupRightEnum.valueOf(addUserGroupRequest.getRight().name()))
                .build();
        final GroupRight groupRight = this.groupInfraService.addUserGroup(groupId, addUserGroupInput);
        final UserGroupRightDTO userGroupRightDTO = GroupRightTransformer.toGroupRightDTO(groupRight);
        return new ResponseEntity<>(userGroupRightDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUserFromGroup(UUID groupId, UUID userId) {
        return GroupApi.super.deleteUserFromGroup(groupId, userId);
    }
}
