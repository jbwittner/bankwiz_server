package fr.bankwiz.server.domain.api;

import java.util.List;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.input.GroupCreationInput;

public interface GroupApi {
    Group groupCreation(GroupCreationInput groupCreationInput);
    List<Group> getUserGroups();
}
