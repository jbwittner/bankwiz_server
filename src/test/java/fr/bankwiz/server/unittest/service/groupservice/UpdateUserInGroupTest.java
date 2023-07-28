package fr.bankwiz.server.unittest.service.groupservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.*;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class UpdateUserInGroupTest extends UnitTestBase {

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
    void updateUserInGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final User userToUpdate = this.unitTestFactory.getUser();
        this.unitTestFactory.addUserToGroup(userToUpdate, group, GroupRight.GroupRightEnum.READ);

        final UpdateUserGroupRequest updateUserGroupRequest = new UpdateUserGroupRequest();
        updateUserGroupRequest.setAuthorization(GroupAuthorizationEnum.WRITE);

        final Integer groupId = group.getGroupId();
        final Integer userToUpdateId = userToUpdate.getUserId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.userRepositoryMockFactory.mockFindById(userToUpdateId, userToUpdate);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        final GroupDTO groupDTO = this.groupService.updateUserInGroup(groupId, userToUpdateId, updateUserGroupRequest);

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
}
