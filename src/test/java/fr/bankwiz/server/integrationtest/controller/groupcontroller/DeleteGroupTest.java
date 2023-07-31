package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class DeleteGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void deleteGroupTest() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroup();

        final User anotherUser = this.integrationTestFactory.getUser();

        this.integrationTestFactory.addUserToGroup(user, group, GroupRight.GroupRightEnum.ADMIN);
        this.integrationTestFactory.addUserToGroup(anotherUser, group, GroupRight.GroupRightEnum.READ);

        final Integer userGroupId = group.getUserGroupId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        this.client
                .doDelete(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final User userUpdated =
                this.userRepository.findById(user.getUserAccountId()).orElseThrow();
        final User anotherUserUpdated =
                this.userRepository.findById(anotherUser.getUserAccountId()).orElseThrow();
        final Optional<Group> optionalGroupUpdated = this.groupRepository.findById(userGroupId);

        Assertions.assertAll(
                () -> Assertions.assertFalse(optionalGroupUpdated.isPresent()),
                () -> Assertions.assertEquals(0, userUpdated.getGroupRights().size()),
                () -> Assertions.assertEquals(
                        0, anotherUserUpdated.getGroupRights().size()));
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.GROUP_ID.getUri(userGroupId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final GroupNotExistException exception = new GroupNotExistException(userGroupId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
