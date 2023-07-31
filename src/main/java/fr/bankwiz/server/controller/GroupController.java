package fr.bankwiz.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.openapi.api.GroupApi;
import fr.bankwiz.openapi.model.*;
import fr.bankwiz.server.service.GroupService;

@RestController
public class GroupController implements GroupApi {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<GroupDTO> addUserToGroup(Integer userGroupId, AddUserGroupRequest addUserGroupRequest) {
        final GroupDTO result = this.groupService.addUserToGroup(userGroupId, addUserGroupRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> createGroup(GroupCreationRequest groupCreationRequest) {
        final GroupDTO result = this.groupService.createGroup(groupCreationRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> getGroup(Integer userGroupId) {
        final GroupDTO result = this.groupService.getGroup(userGroupId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GroupDTO>> getGroups() {
        final List<GroupDTO> result = this.groupService.getGroups();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeUserFromGroup(Integer userGroupId, Integer userId) {
        this.groupService.removeUserFromGroup(userGroupId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> updateGroup(Integer userGroupId, GroupUpdateRequest groupUpdateRequest) {
        final GroupDTO result = this.groupService.updateGroup(userGroupId, groupUpdateRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> updateUserInGroup(
            Integer userGroupId, Integer userId, UpdateUserGroupRequest updateUserGroupRequest) {
        final GroupDTO result = this.groupService.updateUserInGroup(userGroupId, userId, updateUserGroupRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(Integer userGroupId) {
        this.groupService.deleteGroup(userGroupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
