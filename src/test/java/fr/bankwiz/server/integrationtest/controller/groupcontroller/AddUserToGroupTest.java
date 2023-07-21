package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;

class AddUserToGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void addUserOk() throws Exception {
        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);
        final Integer groupId = group.getGroupId();

        final User userToAdd = this.integrationTestFactory.getUser();
        final Integer userToAddId = userToAdd.getUserId();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.READ);

        var result = this.client
                .doPost(
                        IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId),
                        admin.getAuthId(),
                        addUserGroupRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()));
    }

    @Test
    void userAlreadyInGroup() throws Exception {

        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);
        final Integer groupId = group.getGroupId();

        final User userToAdd = this.integrationTestFactory.getUser();
        this.integrationTestFactory.addUserToGroup(group, userToAdd, GroupRightEnum.READ);

        final Integer userToAddId = userToAdd.getUserId();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId);

        final var result = this.client.doPost(uri, admin.getAuthId(), addUserGroupRequest);

        final UserAlreadyAccessGroupException userAlreadyAccessGroupException =
                new UserAlreadyAccessGroupException(userToAdd, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, userAlreadyAccessGroupException);
    }

    @Test
    void userNotExist() throws Exception {

        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);
        final Integer groupId = group.getGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId);

        final var result = this.client.doPost(uri, admin.getAuthId(), addUserGroupRequest);

        final UserNotExistException userNotExistException = new UserNotExistException(userToAddId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, userNotExistException);
    }

    @Test
    void userNotAdmin() throws Exception {

        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(admin, GroupRightEnum.WRITE);
        final Integer groupId = group.getGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId);

        final var result = this.client.doPost(uri, admin.getAuthId(), addUserGroupRequest);

        final UserNotAdminException userNotAdminException = new UserNotAdminException(admin, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, userNotAdminException);
    }

    @Test
    void groupNotExist() throws Exception {

        final User admin = this.integrationTestFactory.getUser();
        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final Integer userToAddId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId);

        final var result = this.client.doPost(uri, admin.getAuthId(), addUserGroupRequest);

        final GroupNotExistException groupNotExistException = new GroupNotExistException(groupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, groupNotExistException);
    }
}
