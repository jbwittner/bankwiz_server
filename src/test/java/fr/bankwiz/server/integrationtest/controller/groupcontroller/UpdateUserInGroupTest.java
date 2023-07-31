package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.UpdateUserGroupRequest;
import fr.bankwiz.openapi.model.UserGroupDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class UpdateUserInGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void updateUserInGroupOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToUpdate = this.integrationTestFactory.getUser();
        this.integrationTestFactory.addUserToGroup(userToUpdate, group, GroupRight.GroupRightEnum.READ);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();
        updateUserGroupRequest.setAuthorization(GroupAuthorizationEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = userToUpdate.getUserAccountId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToUpdateId);

        var result = this.client
                .doPut(uri, user.getAuthId(), updateUserGroupRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

        Assertions.assertEquals(2, groupDTO.getUsers().size());

        UserGroupDTO userGroupDTO = groupDTO.getUsers().stream()
                .filter(u -> u.getUser().getUserId().equals(userToUpdateId))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(GroupAuthorizationEnum.WRITE, userGroupDTO.getAuthorization());

        final User userUpdated = this.userRepository.findById(userToUpdateId).orElseThrow();

        Assertions.assertEquals(
                GroupRight.GroupRightEnum.WRITE,
                userUpdated.getGroupRights().get(0).getGroupRightEnum());
    }

    @Test
    void userNoAccessGroupException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToUpdate = this.integrationTestFactory.getUser();

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();
        updateUserGroupRequest.setAuthorization(GroupAuthorizationEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = userToUpdate.getUserAccountId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToUpdateId);

        final var result = this.client.doPut(uri, user.getAuthId(), updateUserGroupRequest);

        final UserNoAccessGroupException exception = new UserNoAccessGroupException(userToUpdate, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void userNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToUpdateId);

        final var result = this.client.doPut(uri, user.getAuthId(), updateUserGroupRequest);

        final UserNotExistException exception = new UserNotExistException(userToUpdateId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToUpdateId);

        final var result = this.client.doPut(uri, user.getAuthId(), updateUserGroupRequest);

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToUpdateId);

        final var result = this.client.doPut(uri, user.getAuthId(), updateUserGroupRequest);

        final GroupNotExistException exception = new GroupNotExistException(userGroupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
