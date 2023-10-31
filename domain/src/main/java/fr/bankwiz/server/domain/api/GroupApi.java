package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.request.GroupCreationRequest;

public interface GroupApi {
    Group groupCreation(GroupCreationRequest groupCreationRequest);
}
