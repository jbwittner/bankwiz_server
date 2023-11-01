package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public final class GroupTransformer {

    private GroupTransformer() {}

    public static GroupIndexDTO toGroupIndexDTO(final Group group) {
        final GroupIndexDTO groupIndexDTO = new GroupIndexDTO();
        groupIndexDTO.setGroupId(group.getGroupId());
        groupIndexDTO.setGroupName(group.getGroupName());
        return groupIndexDTO;
    }

    public static Group fromGroupEntity(final GroupEntity groupEntity) {
        return Group.builder()
                .groupId(groupEntity.getGroupId())
                .groupName(groupEntity.getGroupName())
                .build();
    }

    public static GroupEntity toGroupEntity(final Group group) {
        return GroupEntity.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .build();
    }
}
