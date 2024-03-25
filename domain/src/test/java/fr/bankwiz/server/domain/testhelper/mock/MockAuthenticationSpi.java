package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.spi.AuthenticationDomainSpi;

public class MockAuthenticationSpi extends MockHelper<AuthenticationDomainSpi> {

    public MockAuthenticationSpi() {
        super(AuthenticationDomainSpi.class);
    }

    public void mockGetUserAuthentication(final UserAuthenticationDomain userAuthenticationDomain) {
        Mockito.doReturn(userAuthenticationDomain).when(this.mock).getUserAuthentication();
    }

    public void checkGetUserAuthenticationCalled() {
        Mockito.verify(this.mock).getUserAuthentication();
    }
}
