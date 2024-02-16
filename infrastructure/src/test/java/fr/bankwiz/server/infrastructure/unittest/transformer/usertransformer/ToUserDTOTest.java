package fr.bankwiz.server.infrastructure.unittest.transformer.usertransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToUserDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final UserDomain user = this.factory.getUser();
        final UserDTO userDTO = UserTransformer.toUserDTO(user);
        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getId(), userDTO.getId()));
    }
}
