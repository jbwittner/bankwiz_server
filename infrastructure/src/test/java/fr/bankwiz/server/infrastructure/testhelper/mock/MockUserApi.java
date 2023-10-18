package fr.bankwiz.server.infrastructure.testhelper.mock;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;

public class MockUserApi extends MockHelper<UserApi> {

    public MockUserApi() {
        super(UserApi.class);
    }

    public MockUserApi mockCheckRegistration(final User user) {
        Mockito.when(this.mock.checkRegistration()).thenReturn(user);
        return this;
    }

    public MockUserApi mockGetCurrentUser(final User user) {
        Mockito.when(this.mock.getCurrentUser()).thenReturn(user);
        return this;
    }
}