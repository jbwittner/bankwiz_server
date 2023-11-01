package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;

public interface GroupApi {
    Group groupCreation(GroupCreationInput groupCreationInput);
}
