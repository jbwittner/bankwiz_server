package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

public class GetGroupsTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getGroupsOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final List<Group> groups = new ArrayList<>();
        groups.add(this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));
        groups.add(this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));
        groups.add(this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));

        final String uri = IntegrationMVCClient.UriEnum.GROUPS.getUri();

        var result = this.client
                .doGet(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final List<GroupDTO> groupDTOs =
                IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, GroupDTO.class);

        Assertions.assertEquals(3, groupDTOs.size());

        groups.forEach(g -> {
            GroupDTO groupDTO = groupDTOs.stream()
                    .filter(gDto -> gDto.getGroupId().equals(g.getGroupId()))
                    .findFirst()
                    .orElseThrow();
            Assertions.assertEquals(g.getGroupName(), groupDTO.getGroupName());
        });
    }
}
