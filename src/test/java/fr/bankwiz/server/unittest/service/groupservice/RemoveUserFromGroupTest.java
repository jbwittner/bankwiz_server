package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class RemoveUserFromGroupTest extends UnitTestBase {

    private GroupService groupService;

    private AuthenticationFacade mockAuthenticationFacade;

    @Override
    protected void initDataBeforeEach() {
        this.mockAuthenticationFacade = Mockito.mock(AuthenticationFacade.class);

        this.groupService = new GroupService(
                this.mockAuthenticationFacade,
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

        final Integer groupId = group.getGroupId();
        final Integer userToRemoveId = userToRemove.getUserId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToRemoveId, userToRemove);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        this.groupService.removeUserFromGroup(groupId, userToRemoveId);

        Assertions.assertEquals(1, group.getGroupRights().size());
        final GroupRight groupRight = group.getGroupRights().get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        GroupRight.GroupRightEnum.ADMIN.name(),
                        groupRight.getGroupRightEnum().name()),
                () -> Assertions.assertEquals(
                        user.getUserId(), groupRight.getUser().getUserId()),
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

        final Integer groupId = group.getGroupId();
        final Integer userToRemoveId = userToRemove.getUserId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToRemoveId, userToRemove);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(UserNoAccessGroupException.class, () -> {
            this.groupService.removeUserFromGroup(groupId, userToRemoveId);
        });
    }

    @Test
    void userNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final Integer groupId = group.getGroupId();
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.groupService.removeUserFromGroup(groupId, userToRemoveId);
        });
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final Integer groupId = group.getGroupId();
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.removeUserFromGroup(groupId, userToRemoveId);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();

        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);
        final Integer userToRemoveId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(groupId, Optional.empty());
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.removeUserFromGroup(groupId, userToRemoveId);
        });
    }
}
