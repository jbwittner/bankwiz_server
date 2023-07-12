package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserUpdateRequest;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;

@Service
public class UserService {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public UserService(final AuthenticationFacade authenticationFacade, final UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    public UserDTO getCurrentUserInfo() {
        final User user = this.authenticationFacade.getCurrentUser();
        return null;
    }

    public UserDTO checkRegistration() {
        return null;
    }

    public UserDTO getUser(final Integer userId) {
        return null;
    }

    public UserDTO updateUser(final UserUpdateRequest userUpdateRequest) {
        return null;
    }

    public List<UserDTO> getUsers() {
        return null;
    }
}
