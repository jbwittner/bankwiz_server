package fr.bankwiz.server.unittest.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.UserGroupDTO;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetGroupTest extends UnitTestBase {

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
    void getGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final User anotherUser = this.unitTestFactory.getUser();
        this.unitTestFactory.addUserToGroup(anotherUser, group, GroupRight.GroupRightEnum.READ);

        final Integer groupId = group.getGroupId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        final GroupDTO groupDTO = this.groupService.getGroup(groupId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, groupDTO.getUsers().size()),
                () -> Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName()),
                () -> Assertions.assertEquals(group.getGroupId(), groupDTO.getGroupId()),
                () -> {
                    group.getGroupRights().forEach(groupRight -> {
                        UserGroupDTO userGroupDTO = groupDTO.getUsers().stream()
                                .filter(u -> u.getUser()
                                        .getUserId()
                                        .equals(groupRight.getUser().getUserId()))
                                .findFirst()
                                .orElseThrow();

                        Assertions.assertEquals(
                                groupRight.getGroupRightEnum().name(),
                                userGroupDTO.getAuthorization().getValue());
                    });
                });
    }

    @Test
    void userNoReadRightException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();
        final Integer groupId = group.getGroupId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.groupService.getGroup(groupId);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();
        final Integer groupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.groupRepositoryMockFactory.mockFindById(groupId, Optional.empty());
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.getGroup(groupId);
        });
    }
}
