package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class RemoveUserFromGroupTest extends UnitTestBase {

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
    void removeUserFromGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToRemove = this.unitTestFactory.getUser();
        this.unitTestFactory.addUserToGroup(userToRemove, group, GroupRight.GroupRightEnum.READ);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, group.getGroupRights().size()),
                () -> Assertions.assertEquals(1, userToRemove.getGroupRights().size()));

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToRemoveId = userToRemove.getUserAccountId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToRemoveId, userToRemove);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        this.groupService.removeUserFromGroup(userGroupId, userToRemoveId);

        Assertions.assertEquals(1, group.getGroupRights().size());
        final GroupRight groupRight = group.getGroupRights().get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        GroupRight.GroupRightEnum.ADMIN.name(),
                        groupRight.getGroupRightEnum().name()),
                () -> Assertions.assertEquals(
                        user.getUserAccountId(), groupRight.getUser().getUserAccountId()),
                () -> Assertions.assertEquals(0, userToRemove.getGroupRights().size()));
    }

    @Test
    void userNoAccessGroupException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToRemove = this.unitTestFactory.getUser();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, group.getGroupRights().size()),
                () -> Assertions.assertEquals(0, userToRemove.getGroupRights().size()));

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToRemoveId = userToRemove.getUserAccountId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToRemoveId, userToRemove);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNoAccessGroupException.class, () -> {
            this.groupService.removeUserFromGroup(userGroupId, userToRemoveId);
        });
    }

    @Test
    void userNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.groupService.removeUserFromGroup(userGroupId, userToRemoveId);
        });
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.removeUserFromGroup(userGroupId, userToRemoveId);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, Optional.empty());
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.removeUserFromGroup(userGroupId, userToRemoveId);
        });
    }
}
