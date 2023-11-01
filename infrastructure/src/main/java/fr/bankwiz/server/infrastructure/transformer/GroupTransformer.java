package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public final class GroupTransformer {

    private GroupTransformer() {}

    public static GroupDTO toGroupDTO(final Group group) {
        final GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(group.getGroupUuid());
        groupDTO.setGroupName(group.getGroupName());
        return groupDTO;
    }

    public static Group fromGroupEntity(final GroupEntity groupEntity) {
        return Group.builder()
                .groupUuid(groupEntity.getGroupId())
                .groupName(groupEntity.getGroupName())
                .build();
    }

    public static GroupEntity toGroupEntity(final Group group) {
        return GroupEntity.builder()
                .groupId(group.getGroupUuid())
                .groupName(group.getGroupName())
                .build();
    }
    
}
