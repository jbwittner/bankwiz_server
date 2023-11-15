package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupDetails;

public class GroupDetailsTransformer {

    private GroupDetailsTransformer() {}

    public static GroupDetailsDTO toGroupDetailsDTO(final GroupDetails groupDetails) {
        final Group group = groupDetails.getGroup();
        final List<UserGroupRightDTO> userGroupRightDTOs =
                GroupRightTransformer.toGroupRightDTO(groupDetails.getGroupRights());
        return new GroupDetailsDTO(group.getGroupId(), group.getGroupName(), userGroupRightDTOs);
    }
}
