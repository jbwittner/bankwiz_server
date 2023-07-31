package fr.bankwiz.server.dto;

import java.util.List;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.openapi.model.UserGroupDTO;
import fr.bankwiz.server.model.Group;

public class GroupDTOBuilder implements Transformer<Group, GroupDTO> {

    private static final UserGroupDTOBuilder USER_GROUP_DTO_BUILDER = new UserGroupDTOBuilder();

    public GroupDTO transform(final Group input) {
        final List<UserGroupDTO> userDTOs = USER_GROUP_DTO_BUILDER.transformAll(input.getGroupRights());
        return new GroupDTO(input.getUserGroupId(), input.getName(), userDTOs);
    }
}
