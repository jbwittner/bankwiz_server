package fr.bankwiz.server.testhelper;

import org.mockito.Mockito;

import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import lombok.Getter;

@Getter
public class AuthenticationFacadeMockFactory {

    private final AuthenticationFacade authenticationFacade;

    public AuthenticationFacadeMockFactory() {
        this.authenticationFacade = Mockito.mock(AuthenticationFacade.class);
    }

    public AuthenticationFacadeMockFactory mockGetCurrentUser(User user) {
        Mockito.when(this.authenticationFacade.getCurrentUser()).thenReturn(user);
        return this;
    }

    public AuthenticationFacadeMockFactory mockGetIdData(AuthenticationFacade.IdData idData) {
        Mockito.when(this.authenticationFacade.getIdData()).thenReturn(idData);
        return this;
    }
}
