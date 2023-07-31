package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserAlreadyAccessGroupException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class AddUserToGroupTest extends UnitTestBase {

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
    void addUserOk() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);

        final Integer userGroupId = group.getUserGroupId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(admin);

        final User userToAdd = this.unitTestFactory.getUser();
        final Integer userToAddId = userToAdd.getUserId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, userToAdd);
        this.groupRightRepositoryMockFactory.mockSave();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.READ);

        final GroupDTO groupDTO = this.groupService.addUserToGroup(userGroupId, addUserGroupRequest);

        var argumentCaptor = this.groupRightRepositoryMockFactory.verifySaveCalled(GroupRight.class);
        final GroupRight rightSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()),
                () -> Assertions.assertEquals(userToAddId, rightSaved.getUser().getUserId()),
                () -> {
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(
                                    1, rightSaved.getUser().getGroupRights().size()),
                            () -> Assertions.assertEquals(GroupRightEnum.READ, rightSaved.getGroupRightEnum()),
                            () -> Assertions.assertEquals(group, rightSaved.getGroup()),
                            () -> Assertions.assertEquals(userToAdd, rightSaved.getUser()));
                });
    }

    @Test
    void userAlreadyInGroup() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);

        final Integer userGroupId = group.getUserGroupId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(admin);

        final User userToAdd = this.unitTestFactory.getUser();
        final GroupRight groupRight = this.unitTestFactory.getGroupRight(userToAdd, group, GroupRightEnum.READ);
        group.addGroupRight(groupRight);

        final Integer userToAddId = userToAdd.getUserId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, userToAdd);

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserAlreadyAccessGroupException.class,
                () -> this.groupService.addUserToGroup(userGroupId, addUserGroupRequest));
    }

    @Test
    void userNotExist() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(admin, GroupRightEnum.ADMIN);

        final Integer userGroupId = group.getUserGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        this.authenticationFacadeMockFactory.mockGetCurrentUser(admin);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserNotExistException.class, () -> this.groupService.addUserToGroup(userGroupId, addUserGroupRequest));
    }

    @Test
    void userNotAdmin() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final Integer userGroupId = group.getUserGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        this.authenticationFacadeMockFactory.mockGetCurrentUser(admin);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserNotAdminException.class, () -> this.groupService.addUserToGroup(userGroupId, addUserGroupRequest));
    }

    @Test
    void groupNotExist() {
        final User admin = this.unitTestFactory.getUser();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final Integer userToAddId = admin.getUserId() + 1;

        this.authenticationFacadeMockFactory.mockGetCurrentUser(admin);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                GroupNotExistException.class, () -> this.groupService.addUserToGroup(userGroupId, addUserGroupRequest));
    }
}
