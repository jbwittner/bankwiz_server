package fr.bankwiz.server.infrastructure.service.userinfraservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.service.UserInfraService;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class GetCurrentUserTest extends InfrastructureUnitTestBase {

    private UserInfraService userInfraService;
    private UserApi userApi;

    @Override
    protected void initDataBeforeEach() {
        this.userApi = Mockito.mock(UserApi.class);
        this.userInfraService = new UserInfraService(userApi);
    }

    @Test
    void getCurrentUser() {
        final User user = this.factory.getUser();
        Mockito.when(this.userApi.getCurrentUser()).thenReturn(user);

        final UserDTO userDTO = this.userInfraService.getCurrentUser();

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getUserUuid(), userDTO.getId()));
    }
}
