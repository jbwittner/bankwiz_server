package fr.bankwiz.server.infrastructure.unittest.transformer.usertransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FromUserEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final UserEntity userEntity = this.factory.getUserEntity();
        final User user = UserTransformer.fromUserEntity(userEntity);
        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getEmail(), user.getEmail()),
                () -> Assertions.assertEquals(userEntity.getAuthId(), user.getAuthId()),
                () -> Assertions.assertEquals(userEntity.getId(), user.getId()));
    }
}
