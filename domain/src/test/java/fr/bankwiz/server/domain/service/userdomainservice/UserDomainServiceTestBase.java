package fr.bankwiz.server.domain.service.userdomainservice;

import fr.bankwiz.server.domain.model.service.UserDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.testhelper.mock.MockAuthenticationSpi;
import fr.bankwiz.server.domain.testhelper.mock.MockUserSpi;

public abstract class UserDomainServiceTestBase extends DomainUnitTestBase {
    protected UserDomainService userDomainService;
    protected MockAuthenticationSpi mockAuthenticationSpi;
    protected MockUserSpi mockUserSpi;

    @Override
    protected void initDataBeforeEach() {
        this.mockAuthenticationSpi = new MockAuthenticationSpi();
        this.mockUserSpi = new MockUserSpi();
        this.userDomainService =
                new UserDomainService(this.mockAuthenticationSpi.getMock(), this.mockUserSpi.getMock());
    }
}
