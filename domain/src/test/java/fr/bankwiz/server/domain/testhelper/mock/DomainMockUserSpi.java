package fr.bankwiz.server.domain.testhelper.mock;

import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.UserSpi;

public class DomainMockUserSpi extends DomainMockHelper<UserSpi> {

    public DomainMockUserSpi() {
        super(UserSpi.class);
    }

    public DomainMockUserSpi mockFindByAuthId(final String authId, final Optional<UserDomain> optionalUser) {
        Mockito.when(this.mock.findByAuthId(authId)).thenReturn(optionalUser);
        return this;
    }

    public DomainMockUserSpi mockFindById(final UUID id, final Optional<UserDomain> optionalUser) {
        Mockito.when(this.mock.findById(id)).thenReturn(optionalUser);
        return this;
    }

    public DomainMockUserSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<UserDomain>getArgument(0);
        });
        return this;
    }
}
