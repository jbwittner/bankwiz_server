package fr.bankwiz.server.infrastructure.unitest.spi.userspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import fr.bankwiz.server.domain.model.UserDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

class SaveTest extends UserSpiImplUnitestBase {

    @Test
    void ok() {

        // Mock save method
        final Answer<UserEntity> returnArgumentAnswer = invocation -> {
            final Object[] args = invocation.getArguments();
            return (UserEntity) args[0];
        };

        Mockito.doAnswer(returnArgumentAnswer).when(this.userEntityRepository).save(Mockito.any(UserEntity.class));

        final UserDomain userDomain = this.factory.getUserDomain();
        final UserDomain userDomainSaved = this.userSpiImpl.save(userDomain);
        Assertions.assertEquals(userDomain, userDomainSaved);

        // Check the save argument
        final ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(this.userEntityRepository).save(argumentCaptor.capture());
        final UserEntity userEntitySaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userDomain.id(), userEntitySaved.getId()),
                () -> Assertions.assertEquals(userDomain.email(), userEntitySaved.getEmail()),
                () -> Assertions.assertEquals(userDomain.authId(), userEntitySaved.getAuthId()));
    }
}
