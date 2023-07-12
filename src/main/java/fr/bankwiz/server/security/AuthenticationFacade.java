package fr.bankwiz.server.security;

import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    private final UserRepository userRepository;

    public AuthenticationFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return null;
    }

}
