package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;

public class DomainMockAuthenticationSpi extends DomainMockHelper<AuthenticationSpi> {

    public DomainMockAuthenticationSpi() {
        super(AuthenticationSpi.class);
    }

    public DomainMockAuthenticationSpi mockGetUserAuthentication(final UserAuthentication userAuthentication) {
        Mockito.when(this.mock.getUserAuthentication()).thenReturn(userAuthentication);
        return this;
    }

    public DomainMockAuthenticationSpi mockGetCurrentUser(final UserDomain user) {
        Mockito.when(this.mock.getCurrentUser()).thenReturn(user);
        return this;
    }
}
