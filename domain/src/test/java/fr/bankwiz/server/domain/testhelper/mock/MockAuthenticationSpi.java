package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;

public class MockAuthenticationSpi extends MockHelper<AuthenticationSpi> {

    public MockAuthenticationSpi() {
        super(AuthenticationSpi.class);
    }

    public MockAuthenticationSpi mockGetUserAuthentication(final UserAuthentication userAuthentication) {
        Mockito.when(this.mock.getUserAuthentication()).thenReturn(userAuthentication);
        return this;
    }

    public MockAuthenticationSpi mockGetCurrentUser(final User user) {
        Mockito.when(this.mock.getCurrentUser()).thenReturn(user);
        return this;
    }
}
