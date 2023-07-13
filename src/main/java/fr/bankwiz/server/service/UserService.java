package fr.bankwiz.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.dto.UserDTOBuilder;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;

@Service
public class UserService {

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public UserService(final AuthenticationFacade authenticationFacade, final UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    public UserDTO getCurrentUserInfo() {
        return null;
    }

    public UserDTO checkRegistration() {
        final String authId = this.authenticationFacade.getCurrentAuthId();
        final Optional<User> optionalUser = this.userRepository.findByAuthId(authId);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            final AuthenticationFacade.IdData idData = this.authenticationFacade.getIdData();
            user = User.builder()
                    .email(idData.email)
                    .authId(idData.sub)
                    .firstName(idData.given_name)
                    .lastName(idData.family_name)
                    .build();
            user = this.userRepository.save(user);
        }

        return USER_DTO_BUILDER.transform(user);
    }

    public UserDTO getUser(final Integer userId) {
        final User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId));
        return USER_DTO_BUILDER.transform(user);
    }

    public List<UserDTO> getUsers() {
        final var users = this.userRepository.findAll();
        return USER_DTO_BUILDER.transformAll(users);
    }
}
