package fr.bankwiz.server.infrastructure.transformer.usertransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

class ToUserDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final User user = this.factory.getUser();
        final UserDTO userDTO = UserTransformer.toUserDTO(user);
        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getUserId(), userDTO.getId()));
    }
}
