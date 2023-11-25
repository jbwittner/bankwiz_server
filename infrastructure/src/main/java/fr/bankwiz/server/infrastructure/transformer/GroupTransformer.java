package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public final class GroupTransformer {

    private GroupTransformer() {}

    public static GroupIndexDTO toGroupIndexDTO(final Group group) {
        final GroupIndexDTO groupIndexDTO = new GroupIndexDTO();
        groupIndexDTO.setGroupId(group.getId());
        groupIndexDTO.setGroupName(group.getGroupName());
        return groupIndexDTO;
    }

    public static Group fromGroupEntity(final GroupEntity groupEntity) {
        return Group.builder()
                .id(groupEntity.getId())
                .groupName(groupEntity.getGroupName())
                .build();
    }

    public static GroupEntity toGroupEntity(final Group group) {
        return GroupEntity.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .build();
    }

    public static List<GroupIndexDTO> toGroupIndexDTO(final List<Group> groups) {
        return groups.stream().map(GroupTransformer::toGroupIndexDTO).toList();
    }
}
