package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.UserGroupDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class GetGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getGroupOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final User anotherUser = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        this.integrationTestFactory.addUserToGroup(anotherUser, group, GroupRight.GroupRightEnum.READ);

        final Integer userGroupId = group.getUserGroupId();
        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()),
                () -> Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName()),
                () -> Assertions.assertEquals(group.getUserGroupId(), groupDTO.getGroupId()),
                () -> {
                    group.getGroupRights().forEach(groupRight -> {
                        UserGroupDTO userGroupDTO = groupDTO.getUsers().stream()
                                .filter(u -> u.getUser()
                                        .getUserId()
                                        .equals(groupRight.getUser().getUserAccountId()))
                                .findFirst()
                                .orElseThrow();

                        Assertions.assertEquals(
                                groupRight.getGroupRightEnum().name(),
                                userGroupDTO.getAuthorization().getValue());
                    });
                });
    }

    @Test
    void userNoReadRightException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroup();

        final Integer userGroupId = group.getUserGroupId();
        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        final UserNoReadRightException exception = new UserNoReadRightException(user, group);

        var result = this.client.doGet(uri, user.getAuthId());

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);
        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        var result = this.client.doGet(uri, user.getAuthId());

        final GroupNotExistException exception = new GroupNotExistException(userGroupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
