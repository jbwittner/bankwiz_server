package fr.bankwiz.server.infrastructure.spi;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import fr.bankwiz.server.domain.exception.UserNotExistException;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.UserSpi;
import lombok.Data;

@Component
public class AuthenticationSpiImpl implements AuthenticationSpi {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String domain;

    private UserSpi userSpi;

    public AuthenticationSpiImpl(final UserSpi userSpi) {
        this.userSpi = userSpi;
    }

    private JwtAuthenticationToken getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }

    @Override
    public UserAuthentication getUserAuthentication() {
        final IdData idData = this.getIdData();
        return UserAuthentication.builder()
                .email(idData.getEmail())
                .sub(idData.getSub())
                .build();
    }

    @Override
    public User getCurrentUser() {
        final JwtAuthenticationToken jwtAuthenticationToken = this.getAuthentication();
        final String subject = jwtAuthenticationToken.getToken().getSubject();
        final Optional<User> optional = userSpi.findByAuthId(subject);
        if(optional.isEmpty()){
            throw new UserNotExistException(subject);
        }
        return optional.get();
    }

    private IdData getIdData() {
        final String accessToken = this.getAuthentication().getToken().getTokenValue();
        final WebClient webClient = WebClient.create(domain);
        final WebClient.ResponseSpec responseSpec = webClient
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
