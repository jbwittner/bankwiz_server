package fr.bankwiz.server.unittest.service.groupservice;

import org.mockito.Mockito;

import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class AddUserToGroupTest extends UnitTestBase {

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
}
