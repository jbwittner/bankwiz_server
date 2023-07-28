package fr.bankwiz.server.unittest.service.groupservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class DeleteGroupTest extends UnitTestBase {

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
    void deleteGroupTest() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final User anotherUser = this.unitTestFactory.getUser();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.unitTestFactory.getGroupRight(user, group, GroupRight.GroupRightEnum.ADMIN));
        groupRights.add(this.unitTestFactory.getGroupRight(anotherUser, group, GroupRight.GroupRightEnum.READ));

        final Integer groupId = group.getGroupId();

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);
        this.groupRightRepositoryMockFactory.mockFindAllByGroup(group, groupRights);
        this.groupRepositoryMockFactory.mockFindById(groupId, group);

        this.groupService.deleteGroup(groupId);

        this.groupRepositoryMockFactory.verifyDeleteCalled(group);

        groupRights.forEach(groupRight -> {
            this.groupRightRepositoryMockFactory.verifyDeleteCalled(groupRight);
        });

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, group.getGroupRights().size()),
                () -> Assertions.assertEquals(0, user.getGroupRights().size()),
                () -> Assertions.assertEquals(0, anotherUser.getGroupRights().size()));
    }
}
