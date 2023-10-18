package fr.bankwiz.server.infrastructure.spi.authenticationspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.testhelper.mock.MockUserSpi;
import fr.bankwiz.server.infrastructure.spi.AuthenticationSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.MockWebClient;

class GetCurrentUserTest extends InfrastructureUnitTestBase {

    private AuthenticationSpiImpl authenticationSpiImpl;
    private MockWebClient mockWebClient;

    @Override
    protected void initDataBeforeEach() {
        this.mockWebClient = new MockWebClient();

        final WebClient.Builder mockWebClientBuilder = Mockito.mock(WebClient.Builder.class);
        Mockito.when(mockWebClientBuilder.baseUrl(ArgumentMatchers.any()).build()).thenReturn(this.mockWebClient.getMock());

        this.authenticationSpiImpl = new AuthenticationSpiImpl(this.faker.internet().domainName(), mockWebClientBuilder, this.mockUserSpi.getMock());
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
