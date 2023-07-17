package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;

public class AddUserToGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {
    }

    @Test
    void addUserOk() throws Exception {
        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRigh(admin, GroupRightEnum.ADMIN);
        final Integer groupId = group.getGroupId();

        final User userToAdd = this.integrationTestFactory.getUser();
        final Integer userToAddId = userToAdd.getUserId();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.READ);

        var result = this.client.doPost(IntegrationMVCClient.UriEnum.GROUP_ID_USER.getUri(groupId), admin.getAuthId(), addUserGroupRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

                Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()));



    }
    
}
