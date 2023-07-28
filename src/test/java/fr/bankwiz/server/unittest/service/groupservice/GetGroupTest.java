package fr.bankwiz.server.unittest.service.groupservice;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetGroupTest extends UnitTestBase {

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
    void getGroupOk() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final User anotherUser = this.unitTestFactory.getUser();
        this.unitTestFactory.addUserToGroup(anotherUser, group, GroupRight.GroupRightEnum.READ);

        final Integer groupId = group.getGroupId();

        this.groupRepositoryMockFactory.mockFindById(groupId, group);
        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(user);

        final GroupDTO groupDTO = this.groupService.getGroup(groupId);

    }


}
