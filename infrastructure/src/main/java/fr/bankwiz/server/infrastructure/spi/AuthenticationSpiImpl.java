package fr.bankwiz.server.infrastructure.spi;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import fr.bankwiz.server.domain.exception.UserNotExistException;
import fr.bankwiz.server.domain.model.data.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.UserSpi;
import lombok.Data;

@Component
public class AuthenticationSpiImpl implements AuthenticationSpi {

    private final UserSpi userSpi;
    private final WebClient webClient;

    public AuthenticationSpiImpl(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") final String domain,
            final WebClient.Builder webClientBuilder,
            final UserSpi userSpi) {
        this.webClient = webClientBuilder.baseUrl(domain).build();
        this.userSpi = userSpi;
    }

    private JwtAuthenticationToken getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }

    @Override
    public UserAuthenticationDomain getUserAuthentication() {
        final IdData idData = this.getIdData();
        return UserAuthenticationDomain.builder()
                .email(idData.getEmail())
                .sub(idData.getSub())
                .build();
    }

    @Override
    public UserDomain getCurrentUser() {
        final JwtAuthenticationToken jwtAuthenticationToken = this.getAuthentication();
        final String subject = jwtAuthenticationToken.getToken().getSubject();
        final Optional<UserDomain> optional = userSpi.findByAuthId(subject);
        if (optional.isEmpty()) {
            throw new UserNotExistException(subject);
        }
        return optional.get();
    }

    private IdData getIdData() {
        final String accessToken = this.getAuthentication().getToken().getTokenValue();
        final WebClient.ResponseSpec responseSpec = this.webClient
                .get()
                .uri("/userinfo")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve();
        return responseSpec.bodyToMono(IdData.class).block(Duration.ofMinutes(1));
    }

    @Data
    public static class IdData {

        private String sub;
        private String name;
        private String email;
    }
}
