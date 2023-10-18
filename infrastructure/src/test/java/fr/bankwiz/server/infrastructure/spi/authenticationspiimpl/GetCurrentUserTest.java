package fr.bankwiz.server.infrastructure.spi.authenticationspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.spi.AuthenticationSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class GetCurrentUserTest extends InfrastructureUnitTestBase {

    private AuthenticationSpiImpl authenticationSpiImpl;

    @Override
    protected void initDataBeforeEach() {
        this.authenticationSpiImpl = new AuthenticationSpiImpl(null, null, null);
    }

    @Test
    void getUserAuthentication() {
        final User user = this.authenticationSpiImpl.getCurrentUser();
        Assertions.assertAll(
                () -> Assertions.assertEquals("toto@email.com", user.getEmail()),
                () -> Assertions.assertEquals("authId", user.getAuthId()),
                () -> Assertions.assertNotNull(user.getUserUuid()));
    }
}
