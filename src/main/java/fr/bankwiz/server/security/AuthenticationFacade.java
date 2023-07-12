package fr.bankwiz.server.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;

@Component
public class AuthenticationFacade {
    private final UserRepository userRepository;

    public AuthenticationFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        final String authId = this.getAuthId();
        return this.userRepository.findByAuthId(authId).orElseThrow(() -> new UserNotExistException(authId));
    }

    public String getAuthId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
