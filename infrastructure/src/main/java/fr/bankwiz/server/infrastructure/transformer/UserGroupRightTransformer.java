package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.server.domain.model.data.GroupRight;

public class UserGroupRightTransformer {

    private UserGroupRightTransformer() {}

    public static UserGroupRightDTO toGroupDetailsDTO(final GroupRight groupRight) {
        final UserDTO userDTO = UserTransformer.toUserDTO(groupRight.getUser());
        return new UserGroupRightDTO(groupRight.getGroupRightId(), userDTO);
    }

    public static List<UserGroupRightDTO> toGroupDetailsDTO(final List<GroupRight> groupRights){
        return groupRights.stream().map(UserGroupRightTransformer::toGroupDetailsDTO).toList();
    }

}
