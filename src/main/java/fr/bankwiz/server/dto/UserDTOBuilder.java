package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.model.User;

public class UserDTOBuilder implements Transformer<User, UserDTO> {

    public UserDTO transform(final User input) {
        return new UserDTO(input.getUserAccountId(), input.getEmail());
    }
}
