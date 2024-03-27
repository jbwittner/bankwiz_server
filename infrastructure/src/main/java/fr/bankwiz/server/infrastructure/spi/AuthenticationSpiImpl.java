package fr.bankwiz.server.infrastructure.spi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.spi.AuthenticationDomainSpi;

@Component
public class AuthenticationSpiImpl implements AuthenticationDomainSpi {

    private final String domain;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationSpiImpl(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") final String domain) {
        this.domain = domain;
    }

    private JwtAuthenticationToken getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }

    @Override
    public UserAuthenticationDomain getUserAuthentication() {
        final String accessToken = this.getAuthentication().getToken().getTokenValue();
        final String url = this.domain + "userinfo";
        final IdData idData = this.sampleApiRequest(url, accessToken, IdData.class);
        return new UserAuthenticationDomain(idData.sub, idData.email);
    }

    private <T> T sampleApiRequest(final String url, final String token, final Class<T> valueType) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();
            final HttpClient client = HttpClient.newHttpClient();
            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), valueType);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record IdData(String sub, String name, String email) {}
}
