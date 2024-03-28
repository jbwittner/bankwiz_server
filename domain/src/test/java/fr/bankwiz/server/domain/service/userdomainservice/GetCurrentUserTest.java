package fr.bankwiz.server.domain.service.userdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.UserDomain;

class GetCurrentUserTest extends UserDomainServiceTestBase {

    @Test
    void currentUserExist() {

        final UserDomain userdomainToFind = this.factory.getUserDomain();
        final String authId = userdomainToFind.authId();

        this.mockAuthenticationSpi.mockGetCurrentUserAuthId(authId);
        this.mockUserSpi.mockFindByAuthId(authId, userdomainToFind);

        final UserDomain userDomain = this.userDomainService.getCurrentUser();

        this.mockAuthenticationSpi.checkGetUserAuthenticationCalled();
        this.mockUserSpi.checkFindByAuthId(authId);

        Assertions.assertEquals(userdomainToFind, userDomain);

    }

    @Test
    void currentNotUserExist() {

        final UserDomain userdomainToFind = this.factory.getUserDomain();
        final String authId = userdomainToFind.authId();

        this.mockAuthenticationSpi.mockGetCurrentUserAuthId(authId);
        this.mockUserSpi.mockFindByAuthId(authId, userdomainToFind);

        final UserDomain userDomain = this.userDomainService.getCurrentUser();

        this.mockAuthenticationSpi.checkGetUserAuthenticationCalled();
        this.mockUserSpi.checkFindByAuthId(authId);

        Assertions.assertEquals(userdomainToFind, userDomain);

    }
    
}
