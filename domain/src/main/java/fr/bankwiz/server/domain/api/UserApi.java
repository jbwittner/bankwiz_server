package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.UserDomain;

public interface UserApi {
    UserDomain checkRegistration();

    UserDomain getCurrentUser();
}
