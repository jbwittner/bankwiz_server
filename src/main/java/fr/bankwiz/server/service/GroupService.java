package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.GroupUpdateRequest;
import fr.bankwiz.openapi.model.UpdateUserGroupRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GroupService {

    public GroupDTO addUserToGroup(Integer groupId, AddUserGroupRequest addUserGroupRequest){
        return null;
    };

    public GroupDTO createGroup(GroupCreationRequest groupCreationRequest){
        return null;
    };

    public GroupDTO getGroup(Integer groupId){
        return null;
    };

    public List<GroupDTO> getGroups(){
        return null;
    };

    public GroupDTO removeUserFromGroup(Integer groupId, Integer userId){
        return null;
    };

    public GroupDTO updateGroup(Integer groupId, GroupUpdateRequest groupUpdateRequest){
        return null;
    };

    public GroupDTO updateUserInGroup(Integer groupId, Integer userId, UpdateUserGroupRequest updateUserGroupRequest){
        return null;
    };

    public void deleteGroup(Integer groupId){
    };
}