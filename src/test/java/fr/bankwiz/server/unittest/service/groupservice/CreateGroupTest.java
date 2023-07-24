package fr.bankwiz.server.unittest.service.groupservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class CreateGroupTest extends UnitTestBase {

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
    void createGroupOk() {
        final User admin = this.unitTestFactory.getUser();

        final GroupCreationRequest groupCreationRequest =
                new GroupCreationRequest(this.faker.lordOfTheRings().location());

        Mockito.when(this.mockAuthenticationFacade.getCurrentUser()).thenReturn(admin);

        this.groupRepositoryMockFactory.mockSave();
        this.groupRightRepositoryMockFactory.mockSave();

        var result = this.groupService.createGroup(groupCreationRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupCreationRequest.getGroupName(), result.getGroupName()), () -> {
                    var users = result.getUsers();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(1, users.size()),
                            () -> Assertions.assertEquals(
                                    admin.getUserId(), users.get(0).getUser().getUserId()));
                });

        var argumentCaptor = this.groupRepositoryMockFactory.verifySaveCalled(Group.class);
        final Group groupSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupCreationRequest.getGroupName(), groupSaved.getGroupName()), () -> {
                    var users = groupSaved.getGroupRights();
                    Assertions.assertAll(
                            () -> Assertions.assertEquals(1, users.size()),
                            () -> Assertions.assertEquals(
                                    admin.getUserId(), users.get(0).getUser().getUserId()));
                });
    }
}
