package fr.bankwiz.server.security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;

@Component
public class AuthenticationFacade {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String domain;

    private final UserRepository userRepository;

    public AuthenticationFacade(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        final String authId = this.getCurrentAuthId();
        return this.userRepository.findByAuthId(authId).orElseThrow(() -> new UserNotExistException(authId));
    }

    public String getCurrentAuthId() {
        return this.getAuthentication().getName();
    }

    public JwtAuthenticationToken getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }

    public IdData getIdData() {
        final String accessToken = this.getAuthentication().getToken().getTokenValue();
        final WebClient webClient = WebClient.create(domain);
        final WebClient.ResponseSpec responseSpec = webClient
                .get()
                .uri("/userinfo")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve();
        return responseSpec.bodyToMono(IdData.class).block(Duration.ofMinutes(1));
    }

    public static class IdData {
        public String sub;
        public String name;
        public String email;
        public String given_name;
        public String family_name;

        @Override
        public String toString() {
            return "IdData{"
                    + "sub='"
                    + sub
                    + '\''
                    + ", name='"
                    + name
                    + '\''
                    + ", email='"
                    + email
                    + '\''
                    + ", given_name='"
                    + given_name
                    + '\''
                    + ", family_name='"
                    + family_name
                    + '\''
                    + '}';
        }
    }
}
