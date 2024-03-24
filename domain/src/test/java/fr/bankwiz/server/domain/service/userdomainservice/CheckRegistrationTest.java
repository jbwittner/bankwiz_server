package fr.bankwiz.server.domain.service.userdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.model.UserDomain;

class CheckRegistrationTest extends UserDomainServiceTestBase {

    @Test
    void checkRegistrationNewUser() {

        UserAuthenticationDomain userAuthenticationDomain = this.factory.getUserAuthentication();
        this.mockAuthenticationSpi.mockGetUserAuthentication(userAuthenticationDomain);
        final String authId = userAuthenticationDomain.sub();

        this.mockUserSpi.mockSave();
        this.mockUserSpi.mockFindByAuthIdEmpty(authId);

        final UserDomain userDomain = this.userDomainService.checkRegistration();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(userDomain),
                () -> Assertions.assertEquals(userAuthenticationDomain.sub(), userDomain.authId()),
                () -> Assertions.assertEquals(userAuthenticationDomain.email(), userDomain.email()));

        this.mockAuthenticationSpi.checkGetUserAuthenticationCalled();
        this.mockUserSpi.checkFindByAuthId(authId);
        this.mockUserSpi.checkSave(userDomain);
    }

    @Test
    void checkRegistrationUserAlreadyExist() {

        final UserAuthenticationDomain userAuthenticationDomain = this.factory.getUserAuthentication();
        this.mockAuthenticationSpi.mockGetUserAuthentication(userAuthenticationDomain);

        final String authId = userAuthenticationDomain.sub();

        this.mockUserSpi.mockSave();

        final UserDomain userDomainAlreadyExist = this.factory.getUserDomain();
        this.mockUserSpi.mockFindByAuthId(authId, userDomainAlreadyExist);

        final UserDomain userDomain = this.userDomainService.checkRegistration();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(userDomain),
                () -> Assertions.assertEquals(userDomainAlreadyExist.id(), userDomain.id()),
                () -> Assertions.assertNotEquals(userDomainAlreadyExist.email(), userDomain.email()),
                () -> Assertions.assertEquals(userAuthenticationDomain.sub(), userDomain.authId()),
                () -> Assertions.assertEquals(userAuthenticationDomain.email(), userDomain.email()));

        this.mockAuthenticationSpi.checkGetUserAuthenticationCalled();
        this.mockUserSpi.checkFindByAuthId(authId);
        this.mockUserSpi.checkSave(userDomain);
    }
}
