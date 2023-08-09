package fr.bankwiz.server.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.GroupUpdateRequest;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class UpdateGroupTest extends UnitTestBase {

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
    void updateGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setGroupName(this.faker.hitchhikersGuideToTheGalaxy().character());

        final String oldGroupName = group.getName();
        final Integer userGroupId = group.getUserGroupId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        final GroupDTO groupDTO = this.groupService.updateGroup(userGroupId, groupUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(oldGroupName, groupDTO.getGroupName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), groupDTO.getGroupName()),
                () -> Assertions.assertNotEquals(oldGroupName, group.getName()),
                () -> Assertions.assertEquals(groupUpdateRequest.getGroupName(), group.getName()));
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer userGroupId = group.getUserGroupId();

        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.updateGroup(userGroupId, groupUpdateRequest);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(userGroupId, Optional.empty());
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.updateGroup(userGroupId, groupUpdateRequest);
        });
    }
}
