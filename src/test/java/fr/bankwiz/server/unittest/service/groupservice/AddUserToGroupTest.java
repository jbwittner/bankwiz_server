package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class AddUserToGroupTest extends UnitTestBase {

    private GroupService groupService;

    private AuthenticationFacade mockAuthenticationFacade;

    @Override
    protected void initDataBeforeEach() {
        this.mockAuthenticationFacade = Mockito.mock(AuthenticationFacade.class);

        this.groupService = new GroupService(
                this.mockAuthenticationFacade,
                this.groupRepositoryMockFactory.getRepository(),
                this.userRepositoryMockFactory.getRepository());
    }

    @Test
    void addUserOk() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(admin, GroupRightEnum.ADMIN);

        final Integer groupId = group.getGroupId();

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        final User userToAdd = this.unitTestFactory.getUser();
        final Integer userToAddId = userToAdd.getUserId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group).mockSave();
        this.userRepositoryMockFactory.mockFindById(userToAddId, userToAdd).mockSave();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.READ);

        final GroupDTO groupDTO = this.groupService.addUserToGroup(groupId, addUserGroupRequest);

        var argumentCaptor = this.userRepositoryMockFactory.verifySaveCalled(User.class);
        final User userSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()),
                () -> Assertions.assertEquals(userToAddId, userSaved.getUserId()),
                () -> {
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(
                                    1, userSaved.getGroupRights().size()),
                            () -> Assertions.assertEquals(
                                    GroupRightEnum.READ,
                                    userSaved.getGroupRights().get(0).getGroupRightEnum()),
                            () -> Assertions.assertEquals(
                                    group, userSaved.getGroupRights().get(0).getGroup()),
                            () -> Assertions.assertEquals(
                                    userToAdd, userSaved.getGroupRights().get(0).getUser()));
                });
    }

    @Test
    void userAlreadyInGroup() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(admin, GroupRightEnum.ADMIN);

        final Integer groupId = group.getGroupId();

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        final User userToAdd = this.unitTestFactory.getUser();
        final GroupRight groupRith = this.unitTestFactory.getGroupRight(userToAdd, group, GroupRightEnum.READ);
        group.addGroupRight(groupRith);

        final Integer userToAddId = userToAdd.getUserId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, userToAdd);

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserAlreadyAccessGroupException.class,
                () -> this.groupService.addUserToGroup(groupId, addUserGroupRequest));
    }

    @Test
    void userNotExist() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(admin, GroupRightEnum.ADMIN);

        final Integer groupId = group.getGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserNotExistException.class, () -> this.groupService.addUserToGroup(groupId, addUserGroupRequest));
    }

    @Test
    void userNotAdmin() {
        final User admin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final Integer groupId = group.getGroupId();

        final Integer userToAddId = admin.getUserId() + 1;

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToAddId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                UserNotAdminException.class, () -> this.groupService.addUserToGroup(groupId, addUserGroupRequest));
    }

    @Test
    void groupNotExist() {
        final User admin = this.unitTestFactory.getUser();

        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final Integer userToAddId = admin.getUserId() + 1;

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        this.groupRepositoryMockFactory.mockFindById(groupId, Optional.empty());

        AddUserGroupRequest addUserGroupRequest = new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.WRITE);

        Assertions.assertThrows(
                GroupNotExistException.class, () -> this.groupService.addUserToGroup(groupId, addUserGroupRequest));
    }
}
