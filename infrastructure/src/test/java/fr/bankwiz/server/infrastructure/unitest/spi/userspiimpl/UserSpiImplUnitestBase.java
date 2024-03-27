package fr.bankwiz.server.infrastructure.unitest.spi.userspiimpl;

import org.mockito.Mockito;

import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import fr.bankwiz.server.infrastructure.unitest.InfrastructureUnitTestBase;

public class UserSpiImplUnitestBase extends InfrastructureUnitTestBase {

    protected UserSpiImpl userSpiImpl;
    protected UserEntityRepository userEntityRepository;

    @Override
    protected void initDataBeforeEach() {
        this.userEntityRepository = Mockito.mock(UserEntityRepository.class);
        this.userSpiImpl = new UserSpiImpl(userEntityRepository);
    }
}
