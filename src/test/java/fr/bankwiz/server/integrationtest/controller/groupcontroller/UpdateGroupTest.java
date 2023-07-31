package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.GroupUpdateRequest;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class UpdateGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void updateGroupOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setGroupName(this.faker.hitchhikersGuideToTheGalaxy().character());

        final String oldGroupName = group.getName();
        final Integer userGroupId = group.getUserGroupId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        var result = this.client
                .doPut(uri, user.getAuthId(), groupUpdateRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

        final Group groupUpdated = this.groupRepository.findById(userGroupId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(oldGroupName, groupDTO.getGroupName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), groupDTO.getGroupName()),
                () -> Assertions.assertNotEquals(oldGroupName, groupUpdated.getName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), groupUpdated.getName()));
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer userGroupId = group.getUserGroupId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        final var result = this.client.doPut(uri, user.getAuthId(), groupUpdateRequest);

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        final var result = this.client.doPut(uri, user.getAuthId(), groupUpdateRequest);

        final GroupNotExistException exception = new GroupNotExistException(userGroupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
