package fr.bankwiz.server.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.*;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class UpdateUserInGroupTest extends UnitTestBase {

    private GroupService groupService;

    @Override
    protected void initDataBeforeEach() {
        this.groupService = new GroupService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.userRepositoryMockFactory.getRepository(),
                this.groupRightRepositoryMockFactory.getRepository());
    }

    @Test
    void updateUserInGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToUpdate = this.unitTestFactory.getUser();
        this.unitTestFactory.addUserToGroup(userToUpdate, group, GroupRight.GroupRightEnum.READ);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();
        updateUserGroupRequest.setAuthorization(GroupAuthorizationEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = userToUpdate.getUserAccountId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToUpdateId, userToUpdate);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        final GroupDTO groupDTO =
                this.groupService.updateUserInGroup(userGroupId, userToUpdateId, updateUserGroupRequest);

        Assertions.assertEquals(2, groupDTO.getUsers().size());

        UserGroupDTO userGroupDTO = groupDTO.getUsers().stream()
                .filter(u -> u.getUser().getUserId().equals(userToUpdateId))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(GroupAuthorizationEnum.WRITE, userGroupDTO.getAuthorization());
        Assertions.assertEquals(
                GroupRight.GroupRightEnum.WRITE,
                userToUpdate.getGroupRights().get(0).getGroupRightEnum());
    }

    @Test
    void userNoAccessGroupException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToUpdate = this.unitTestFactory.getUser();

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();
        updateUserGroupRequest.setAuthorization(GroupAuthorizationEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = userToUpdate.getUserAccountId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToUpdateId, userToUpdate);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNoAccessGroupException.class, () -> {
            this.groupService.updateUserInGroup(userGroupId, userToUpdateId, updateUserGroupRequest);
        });
    }

    @Test
    void userNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToUpdateId, Optional.empty());
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.groupService.updateUserInGroup(userGroupId, userToUpdateId, updateUserGroupRequest);
        });
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.updateUserInGroup(userGroupId, userToUpdateId, updateUserGroupRequest);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);
        final Integer userToUpdateId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, Optional.empty());
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.updateUserInGroup(userGroupId, userToUpdateId, updateUserGroupRequest);
        });
    }
}
