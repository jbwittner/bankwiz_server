package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.openapi.model.UserGroupDTO;
import fr.bankwiz.server.model.GroupRight;

public class UserGroupDTOBuilder implements Transformer<GroupRight, UserGroupDTO> {

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    public UserGroupDTO transform(final GroupRight input) {
        final GroupAuthorizationEnum groupAuthorizationEnum =
                GroupAuthorizationEnum.fromValue(input.getGroupRightEnum().name());
        return new UserGroupDTO(USER_DTO_BUILDER.transform(input.getUser()), groupAuthorizationEnum);
    }
}
