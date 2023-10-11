package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.User;

public class UserTransformer {

    private final UserToUserDTO userToUserDTO = new UserToUserDTO();

    public UserDTO transformToUserDTO(User user) {
        return userToUserDTO.transform(user);
    }

    public class UserToUserDTO implements Transformer<User, UserDTO> {

        @Override
        public UserDTO transform(User input) {
            throw new UnsupportedOperationException("Unimplemented method 'transform'");
        }
    }
}
