package fr.bankwiz.server.domain.testhelper.mock;

import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

public class DomainMockUserSpi extends DomainMockHelper<UserSpi> {

    public DomainMockUserSpi() {
        super(UserSpi.class);
    }

    public DomainMockUserSpi mockFindByAuthId(final String authId, final Optional<User> optionalUser) {
        Mockito.when(this.mock.findByAuthId(authId)).thenReturn(optionalUser);
        return this;
    }

    public DomainMockUserSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<User>getArgument(0);
        });
        return this;
    }
}
