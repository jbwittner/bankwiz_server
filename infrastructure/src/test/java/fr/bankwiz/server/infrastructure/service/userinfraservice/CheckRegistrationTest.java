package fr.bankwiz.server.infrastructure.service.userinfraservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.service.UserInfraService;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.MockUserApi;

class CheckRegistrationTest extends InfrastructureUnitTestBase {

    private UserInfraService userInfraService;
    private MockUserApi mockUserApi;

    @Override
    protected void initDataBeforeEach() {
        this.mockUserApi = new MockUserApi();
        this.userInfraService = new UserInfraService(mockUserApi.getMock());
    }

    @Test
    void checkRegistration() {
        final User user = this.factory.getUser();
        mockUserApi.mockCheckRegistration(user);

        final UserDTO userDTO = this.userInfraService.checkRegistration();

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getUserUuid(), userDTO.getId()));
    }
}
