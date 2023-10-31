package fr.bankwiz.server.domain.service.userdomainservice;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class CheckRegistrationTest extends DomainUnitTestBase {

    private UserDomainService userDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.userDomainService =
                new UserDomainService(this.mockAuthenticationSpi.getMock(), this.mockUserSpi.getMock());
    }

    @Test
    void newUserTest() {
        final UserAuthentication userAuthentication = this.factory.getUserAuthentication();

        this.mockAuthenticationSpi.mockGetUserAuthentication(userAuthentication);
        this.mockUserSpi
                .mockFindByAuthId(userAuthentication.getSub(), Optional.empty())
                .mockSave();

        final User userResult = this.userDomainService.checkRegistration();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(userResult),
                () -> Assertions.assertEquals(userAuthentication.getEmail(), userResult.getEmail()),
                () -> Assertions.assertEquals(userAuthentication.getSub(), userResult.getAuthId()));
    }

    @Test
    void userAlreadyExistTest() {
        final UserAuthentication userAuthentication = this.factory.getUserAuthentication();

        this.mockAuthenticationSpi.mockGetUserAuthentication(userAuthentication);

        final User user = this.factory.getUser();
        Assertions.assertNotEquals(userAuthentication.getEmail(), user.getEmail());

        final String oldEmail = user.getEmail();
        final UUID userUuid = user.getUserUuid();

        this.mockUserSpi
                .mockFindByAuthId(userAuthentication.getSub(), Optional.of(user))
                .mockSave();

        final User userResult = this.userDomainService.checkRegistration();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(user),
                () -> Assertions.assertNotEquals(oldEmail, user.getEmail()),
                () -> Assertions.assertEquals(userAuthentication.getEmail(), user.getEmail()),
                () -> Assertions.assertEquals(user.getAuthId(), user.getAuthId()),
                () -> Assertions.assertEquals(0, userUuid.compareTo(userResult.getUserUuid())));
    }
}
