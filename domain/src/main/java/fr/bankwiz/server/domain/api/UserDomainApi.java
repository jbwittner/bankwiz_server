package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.UserDomain;

public interface UserDomainApi {
    UserDomain checkRegistration();
    UserDomain getCurrentUser();
}
