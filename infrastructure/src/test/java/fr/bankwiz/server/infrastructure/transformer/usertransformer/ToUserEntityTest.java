package fr.bankwiz.server.infrastructure.transformer.usertransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

class ToUserEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final User user = this.factory.getUser();
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getEmail(), userEntity.getEmail()),
                () -> Assertions.assertEquals(user.getAuthId(), userEntity.getAuthId()),
                () -> Assertions.assertEquals(user.getUserId(), userEntity.getUserId()));
    }
}
