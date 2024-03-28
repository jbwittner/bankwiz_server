package fr.bankwiz.server.infrastructure.tools;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.UserDomain;

public class DTOMapper {

    private DTOMapper() {}

    public static UserDTO toUserDTO(final UserDomain userDomain) {
        return new UserDTO(userDomain.id(), userDomain.email());
    }
}
