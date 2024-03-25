package fr.bankwiz.server.domain.testhelper.mock;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.model.spi.UserDomainSpi;

public class MockUserSpi extends MockHelper<UserDomainSpi> {

    public MockUserSpi() {
        super(UserDomainSpi.class);
    }

    public void mockSave() {

        final Answer<UserDomain> returnArgumentAnswer = invocation -> {
            final Object[] args = invocation.getArguments();
            return (UserDomain) args[0];
        };

        Mockito.doAnswer(returnArgumentAnswer).when(this.mock).save(Mockito.any(UserDomain.class));
    }

    public void mockFindByAuthId(final String authId, final UserDomain userDomain) {
        Mockito.doReturn(Optional.of(userDomain)).when(this.mock).findByAuthId(authId);
    }

    public void mockFindByAuthIdEmpty(final String authId) {
        Mockito.doReturn(Optional.empty()).when(this.mock).findByAuthId(authId);
    }

    public void checkSave(final UserDomain userDomainToCheck) {
        final ArgumentCaptor<UserDomain> argumentCaptor = ArgumentCaptor.forClass(UserDomain.class);
        Mockito.verify(this.mock).save(argumentCaptor.capture());
        final UserDomain userDomain = argumentCaptor.getValue();
        Assertions.assertEquals(userDomain, userDomainToCheck);
    }

    public void checkFindByAuthId(final String authId) {
        Mockito.verify(this.getMock()).findByAuthId(authId);
    }
}
