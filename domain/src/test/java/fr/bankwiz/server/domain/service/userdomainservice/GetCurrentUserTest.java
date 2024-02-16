package fr.bankwiz.server.domain.service.userdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class GetCurrentUserTest extends DomainUnitTestBase {

    private UserDomainService userDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.userDomainService =
                new UserDomainService(this.mockAuthenticationSpi.getMock(), this.mockUserSpi.getMock());
    }

    @Test
    void getCurrentUserTest() {
        final UserDomain user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);
        final UserDomain currentUser = this.userDomainService.getCurrentUser();
        Assertions.assertEquals(user, currentUser);
    }
}
