package fr.bankwiz.server.infrastructure.unitest.spi.userspiimpl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public class FindByAuthIdTest extends UserSpiImplUnitestBase {

    @Test
    void userFound() {

        final UserEntity userEntity = this.factory.getUserEntity();

        final String authId = userEntity.getAuthId();

        Mockito.doReturn(Optional.of(userEntity))
                .when(this.userEntityRepository)
                .findByAuthId(authId);

        final Optional<UserDomain> optional = this.userSpiImpl.findByAuthId(authId);

        Mockito.verify(this.userEntityRepository).findByAuthId(authId);

        Assertions.assertTrue(optional.isPresent());

        final UserDomain userDomain = optional.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getId(), userDomain.id()),
                () -> Assertions.assertEquals(userEntity.getEmail(), userDomain.email()),
                () -> Assertions.assertEquals(userEntity.getAuthId(), userDomain.authId()));
    }

    @Test
    void userNotFound() {

        final String authId = this.factory.getAuthId();

        Mockito.doReturn(Optional.empty()).when(this.userEntityRepository).findByAuthId(authId);

        final Optional<UserDomain> optional = this.userSpiImpl.findByAuthId(authId);

        Mockito.verify(this.userEntityRepository).findByAuthId(authId);

        Assertions.assertTrue(optional.isEmpty());
    }
}
