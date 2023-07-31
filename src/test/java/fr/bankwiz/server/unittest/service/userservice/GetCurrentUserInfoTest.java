package fr.bankwiz.server.unittest.service.userservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.UserService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetCurrentUserInfoTest extends UnitTestBase {

    private UserService userService;

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.userRepositoryMockFactory.getRepository());
    }

    @Test
    void GetCurrentUserInfoOk() {
        final User user = this.unitTestFactory.getUser();
        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);
        final UserDTO userDTO = this.userService.getCurrentUserInfo();
        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getUserAccountId(), userDTO.getUserId()),
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getFirstName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(user.getLastName(), userDTO.getLastName()));
    }

    @Test
    void userNotExistException() {
        final AuthenticationFacade authenticationFacade =
                this.authenticationFacadeMockFactory.getAuthenticationFacade();
        Mockito.when(authenticationFacade.getCurrentUser()).thenThrow(new UserNotExistException(""));
        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.userService.getCurrentUserInfo();
        });
    }
}
