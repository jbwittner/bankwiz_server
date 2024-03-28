package fr.bankwiz.server.domain.model.api;

import fr.bankwiz.server.domain.model.model.UserDomain;

public interface UserDomainApi {
    UserDomain checkRegistration();
    UserDomain getCurrentUser();
}
