package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.model.Group;
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

        this.groupRepositoryMockFactory
                .mockFindById(groupId, Optional.of(group))
                .mockSave();
        this.userRepositoryMockFactory
                .mockFindById(userToAddId, Optional.of(userToAdd))
                .mockSave();

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
}
