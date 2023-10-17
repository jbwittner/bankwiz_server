package fr.bankwiz.server.infrastructure.spi.authenticationspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.UserAuthentication;
import fr.bankwiz.server.infrastructure.spi.AuthenticationSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class GetUserAuthenticationTest extends InfrastructureUnitTestBase {

    private AuthenticationSpiImpl authenticationSpiImpl;

    @Override
    protected void initDataBeforeEach() {
        this.authenticationSpiImpl = new AuthenticationSpiImpl();
    }

    @Test
    void getUserAuthentication() {
        final UserAuthentication userAuthentication = this.authenticationSpiImpl.getUserAuthentication();
        Assertions.assertAll(
                () -> Assertions.assertEquals("toto@email.com", userAuthentication.getEmail()),
                () -> Assertions.assertEquals("suuuuuub", userAuthentication.getSub()));
    }
}
