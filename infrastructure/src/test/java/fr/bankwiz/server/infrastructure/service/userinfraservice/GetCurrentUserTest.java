package fr.bankwiz.server.infrastructure.service.userinfraservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.service.UserInfraService;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.InfrastructureMockUserApi;

class GetCurrentUserTest extends InfrastructureUnitTestBase {

    private UserInfraService userInfraService;
    private InfrastructureMockUserApi mockUserApi;

    @Override
    protected void initDataBeforeEach() {
        this.mockUserApi = new InfrastructureMockUserApi();
        this.userInfraService = new UserInfraService(mockUserApi.getMock());
    }

    @Test
    void getCurrentUser() {
        final User user = this.factory.getUser();
        this.mockUserApi.mockGetCurrentUser(user);

        final UserDTO userDTO = this.userInfraService.getCurrentUser();

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getUserUuid(), userDTO.getId()));
    }
}
