package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.User;

public final class UserTransformer {

    private UserTransformer() {}

    public static UserDTO toUserDTO(final User user) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserUuid());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
