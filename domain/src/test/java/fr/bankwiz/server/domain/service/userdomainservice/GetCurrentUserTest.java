package fr.bankwiz.server.domain.service.userdomainservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.testhelper.UnitTestDomainBase;

class GetCurrentUserTest extends UnitTestDomainBase {

    private UserDomainService userDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.userDomainService =
                new UserDomainService(this.mockAuthenticationSpi.getMock(), this.mockUserSpi.getMock());
    }

    @Test
    void getCurrentUserTest() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);
        final User currentUser = this.userDomainService.getCurrentUser();
        Assertions.assertEquals(user, currentUser);
    }
}
