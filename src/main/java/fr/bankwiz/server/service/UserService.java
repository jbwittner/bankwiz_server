package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.dto.UserDTOBuilder;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public UserService(final AuthenticationFacade authenticationFacade, final UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    public UserDTO getCurrentUserInfo() {
        final User user = this.authenticationFacade.getCurrentUser();
        return USER_DTO_BUILDER.transform(user);
    }

    public UserDTO checkRegistration() {
        final AuthenticationFacade.IdData idData = this.authenticationFacade.getIdData();

        User user = this.userRepository.findByAuthId(idData.getSub()).orElse(new User());

        user.setAuthId(idData.getSub());
        user.setEmail(idData.getEmail());
        user = this.userRepository.save(user);

        return USER_DTO_BUILDER.transform(user);
    }

    public UserDTO getUser(final Integer userAccountId) {
        final User user =
                this.userRepository.findById(userAccountId).orElseThrow(() -> new UserNotExistException(userAccountId));
        return USER_DTO_BUILDER.transform(user);
    }

    public List<UserDTO> getUsers() {
        final var users = this.userRepository.findAll();
        return USER_DTO_BUILDER.transformAll(users);
    }
}
