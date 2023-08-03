package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.model.Group;

public class GroupIndexDTOBuilder implements Transformer<Group, GroupIndexDTO> {

    public GroupIndexDTO transform(final Group input) {
        return new GroupIndexDTO(input.getUserGroupId(), input.getName());
    }
}
