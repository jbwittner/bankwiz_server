package fr.bankwiz.server.infrastructure.spi;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.spi.AuthenticationDomainSpi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;


@Component
public class AuthenticationSpiImpl implements AuthenticationDomainSpi {

    private final String domain;

    public AuthenticationSpiImpl(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") final String domain,) {
        this.domain = domain;
    }

    private JwtAuthenticationToken getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }


    @Override
    public UserAuthenticationDomain getUserAuthentication() {
        final IdData idData = this.getIdData();
        return new UserAuthenticationDomain(idData.sub,idData.email);
    }

    private IdData getIdData() {
        final String accessToken = this.getAuthentication().getToken().getTokenValue();
        final String url = this.domain + "/userinfo";
        try {
            final HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).header("Authorization", "Bearer " + accessToken).GET().build();
            final HttpClient client = HttpClient.newHttpClient();
            client.send(request);
            return new IdData("", "", "");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public record IdData(String sub, String name, String email){};
}
