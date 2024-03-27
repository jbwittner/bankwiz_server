package fr.bankwiz.server.infrastructure.unitest.tools.dtomapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.infrastructure.tools.DTOMapper;
import fr.bankwiz.server.infrastructure.unitest.InfrastructureUnitTestBase;

public class ToUserDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final UserDomain userDomain = this.factory.getUserDomain();
        final UserDTO userDTO = DTOMapper.toUserDTO(userDomain);

        Assertions.assertAll(
                () -> Assertions.assertEquals(userDomain.id(), userDTO.getId()),
                () -> Assertions.assertEquals(userDomain.email(), userDTO.getEmail()));
    }
}
