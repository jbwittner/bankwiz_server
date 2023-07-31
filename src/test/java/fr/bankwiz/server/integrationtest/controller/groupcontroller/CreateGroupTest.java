package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;

class CreateGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void createGroupOk() throws Exception {
        final User admin = this.integrationTestFactory.getUser();

        final GroupCreationRequest groupCreationRequest =
                new GroupCreationRequest(this.faker.witcher().witcher());

        var result = this.client
                .doPost(IntegrationMVCClient.UriEnum.GROUP.getUri(), admin.getAuthId(), groupCreationRequest)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final GroupDTO groupDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, GroupDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupCreationRequest.getGroupName(), groupDTO.getGroupName()), () -> {
                    var users = groupDTO.getUsers();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(1, users.size()),
                            () -> Assertions.assertEquals(
                                    admin.getUserAccountId(), users.get(0).getUser().getUserId()));
                });

        final Group group = this.groupRepository.findById(groupDTO.getGroupId()).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupCreationRequest.getGroupName(), group.getName()), () -> {
                    var users = group.getGroupRights();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(1, users.size()),
                            () -> Assertions.assertEquals(
                                    admin.getUserAccountId(), users.get(0).getUser().getUserAccountId()));
                });
    }
}
