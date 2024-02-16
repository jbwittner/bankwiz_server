package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;

public class GroupDetailsTransformer {

    private GroupDetailsTransformer() {}

    public static GroupDetailsDTO toGroupDetailsDTO(final GroupDetailsDomain groupDetails) {
        final GroupDomain group = groupDetails.getGroup();
        final List<UserGroupRightDTO> userGroupRightDTOs =
                GroupRightTransformer.toGroupRightDTO(groupDetails.getGroupRights());
        return new GroupDetailsDTO(group.getId(), group.getGroupName(), userGroupRightDTOs);
    }
}
