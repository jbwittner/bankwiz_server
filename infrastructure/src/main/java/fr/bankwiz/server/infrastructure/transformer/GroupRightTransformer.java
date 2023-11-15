package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.openapi.model.UserGroupRightEnum;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public final class GroupRightTransformer {

    private GroupRightTransformer() {}

    public static GroupRight fromGroupRightEntity(final GroupRightEntity groupRightEntity) {

        final User user = UserTransformer.fromUserEntity(groupRightEntity.getUserEntity());
        final Group group = GroupTransformer.fromGroupEntity(groupRightEntity.getGroupEntity());

        return GroupRight.builder()
                .groupRightId(groupRightEntity.getGroupRightId())
                .groupRightEnum(GroupRightEnum.valueOf(
                        groupRightEntity.getGroupRightEntityEnum().name()))
                .user(user)
                .group(group)
                .build();
    }

    public static GroupRightEntity toGroupRightEntity(final GroupRight groupRight) {

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(groupRight.getGroup());
        final UserEntity userEntity = UserTransformer.toUserEntity(groupRight.getUser());

        return GroupRightEntity.builder()
                .groupEntity(groupEntity)
                .groupRightId(groupRight.getGroupRightId())
                .groupRightEntityEnum(GroupRightEntityEnum.valueOf(
                        groupRight.getGroupRightEnum().name()))
                .userEntity(userEntity)
                .build();
    }

    public static List<GroupRight> fromGroupRightEntity(final List<GroupRightEntity> groupRightEntities) {
        return groupRightEntities.stream()
                .map(GroupRightTransformer::fromGroupRightEntity)
                .toList();
    }

    public static UserGroupRightDTO toGroupDetailsDTO(final GroupRight groupRight) {
        final UserDTO userDTO = UserTransformer.toUserDTO(groupRight.getUser());
        final UserGroupRightEnum userGroupRightEnum =
                UserGroupRightEnum.fromValue(groupRight.getGroupRightEnum().name());

        return new UserGroupRightDTO(groupRight.getGroupRightId(), userDTO, userGroupRightEnum);
    }

    public static List<UserGroupRightDTO> toGroupDetailsDTO(final List<GroupRight> groupRights) {
        return groupRights.stream()
                .map(GroupRightTransformer::toGroupDetailsDTO)
                .toList();
    }
}
