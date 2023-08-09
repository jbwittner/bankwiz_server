package fr.bankwiz.server.service.groupservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.OneToManyElementException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class DeleteGroupTest extends UnitTestBase {

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
    void deleteGroupTest() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final User anotherUser = this.unitTestFactory.getUser();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.unitTestFactory.getGroupRight(user, group, GroupRight.GroupRightEnum.ADMIN));
        groupRights.add(this.unitTestFactory.getGroupRight(anotherUser, group, GroupRight.GroupRightEnum.READ));

        final Integer userGroupId = group.getUserGroupId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRightRepositoryMockFactory.mockFindAllByGroup(group, groupRights);
        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);

        this.groupService.deleteGroup(userGroupId);

        this.groupRepositoryMockFactory.verifyDeleteCalled(group);

        groupRights.forEach(groupRight -> {
            this.groupRightRepositoryMockFactory.verifyDeleteCalled(groupRight);
        });

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, group.getGroupRights().size()),
                () -> Assertions.assertEquals(0, user.getGroupRights().size()),
                () -> Assertions.assertEquals(0, anotherUser.getGroupRights().size()));
    }

    @Test
    void userNotAdminException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final Integer userGroupId = group.getUserGroupId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.groupService.deleteGroup(userGroupId);
        });
    }

    @Test
    void groupNotExistException() {
        final User user = this.unitTestFactory.getUser();

        final Integer userGroupId = this.faker.random().nextInt(Integer.MAX_VALUE);

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(userGroupId, Optional.empty());

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.groupService.deleteGroup(userGroupId);
        });
    }

    @Test
    void oneToManyElementException() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        this.unitTestFactory.getBankAccount(group);

        final Integer userGroupId = group.getUserGroupId();

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        this.groupRepositoryMockFactory.mockFindById(userGroupId, group);

        Assertions.assertThrows(OneToManyElementException.class, () -> {
            this.groupService.deleteGroup(userGroupId);
        });
    }
}
