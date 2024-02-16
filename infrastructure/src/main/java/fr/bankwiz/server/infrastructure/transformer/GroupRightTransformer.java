package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.openapi.model.UserGroupRightEnum;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public final class GroupRightTransformer {

    private GroupRightTransformer() {}

    public static GroupRightDomain fromGroupRightEntity(final GroupRightEntity groupRightEntity) {

        final UserDomain user = UserTransformer.fromUserEntity(groupRightEntity.getUserEntity());
        final GroupDomain group = GroupTransformer.fromGroupEntity(groupRightEntity.getGroupEntity());

        return GroupRightDomain.builder()
                .id(groupRightEntity.getId())
                .groupRightEnum(GroupRightEnum.valueOf(
                        groupRightEntity.getGroupRightEntityEnum().name()))
                .user(user)
                .group(group)
                .build();
    }

    public static GroupRightEntity toGroupRightEntity(final GroupRightDomain groupRight) {

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(groupRight.getGroup());
        final UserEntity userEntity = UserTransformer.toUserEntity(groupRight.getUser());

        return GroupRightEntity.builder()
                .groupEntity(groupEntity)
                .id(groupRight.getId())
                .groupRightEntityEnum(GroupRightEntityEnum.valueOf(
                        groupRight.getGroupRightEnum().name()))
                .userEntity(userEntity)
                .build();
    }

    public static List<GroupRightDomain> fromGroupRightEntity(final List<GroupRightEntity> groupRightEntities) {
        return groupRightEntities.stream()
                .map(GroupRightTransformer::fromGroupRightEntity)
                .toList();
    }

    public static UserGroupRightDTO toGroupRightDTO(final GroupRightDomain groupRight) {
        final UserDTO userDTO = UserTransformer.toUserDTO(groupRight.getUser());
        final UserGroupRightEnum userGroupRightEnum =
                UserGroupRightEnum.fromValue(groupRight.getGroupRightEnum().name());

        return new UserGroupRightDTO(groupRight.getId(), userDTO, userGroupRightEnum);
    }

    public static List<UserGroupRightDTO> toGroupRightDTO(final List<GroupRightDomain> groupRights) {
        return groupRights.stream().map(GroupRightTransformer::toGroupRightDTO).toList();
    }
}
