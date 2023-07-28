package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.GroupUpdateRequest;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class UpdateGroupTest extends UnitTestBase {

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
    void updateGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setGroupName(this.faker.hitchhikersGuideToTheGalaxy().character());

        final String oldGroupName = group.getGroupName();
        final Integer groupId = group.getGroupId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        final GroupDTO groupDTO = this.groupService.updateGroup(groupId, groupUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(oldGroupName, groupDTO.getGroupName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), groupDTO.getGroupName()),
                () -> Assertions.assertNotEquals(oldGroupName, group.getGroupName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), group.getGroupName()));
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer groupId = group.getGroupId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.updateGroup(groupId, groupUpdateRequest);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(groupId, Optional.empty());
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.updateGroup(groupId, groupUpdateRequest);
        });
    }
}
