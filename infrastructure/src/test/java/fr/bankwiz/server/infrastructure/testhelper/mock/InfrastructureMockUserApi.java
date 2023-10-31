package fr.bankwiz.server.infrastructure.testhelper.mock;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.data.User;

public class InfrastructureMockUserApi extends InfrastructureMockHelper<UserApi> {

    public InfrastructureMockUserApi() {
        super(UserApi.class);
    }

    public InfrastructureMockUserApi mockCheckRegistration(final User user) {
        Mockito.when(this.mock.checkRegistration()).thenReturn(user);
        return this;
    }

    public InfrastructureMockUserApi mockGetCurrentUser(final User user) {
        Mockito.when(this.mock.getCurrentUser()).thenReturn(user);
        return this;
    }
}
