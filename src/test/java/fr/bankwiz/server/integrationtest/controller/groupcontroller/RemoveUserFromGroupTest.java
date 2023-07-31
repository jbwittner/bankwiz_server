package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class RemoveUserFromGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void removeUserFromGroupOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToRemove = this.integrationTestFactory.getUser();
        this.integrationTestFactory.addUserToGroup(userToRemove, group, GroupRight.GroupRightEnum.READ);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, group.getGroupRights().size()),
                () -> Assertions.assertEquals(1, userToRemove.getGroupRights().size()));

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToRemoveId = userToRemove.getUserAccountId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID_USER_ID.getUri(userGroupId, userToRemoveId);

        this.client
                .doDelete(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final Group groupResult = this.groupRepository.findById(userGroupId).orElseThrow();
        final User userRemovedResult =
                this.userRepository.findById(userToRemoveId).orElseThrow();

        Assertions.assertEquals(1, groupResult.getGroupRights().size());
        final GroupRight groupRight = groupResult.getGroupRights().get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        GroupRight.GroupRightEnum.ADMIN.name(),
                        groupRight.getGroupRightEnum().name()),
                () -> Assertions.assertEquals(
                        user.getUserAccountId(), groupRight.getUser().getUserAccountId()),
                () -> Assertions.assertEquals(
                        0, userRemovedResult.getGroupRights().size()));
    }
}
