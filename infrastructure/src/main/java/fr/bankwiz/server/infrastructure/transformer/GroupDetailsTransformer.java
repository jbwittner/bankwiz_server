package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupDetails;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;

public class GroupDetailsTransformer {

    public static GroupDetailsDTO toGroupDetailsDTO(final GroupDetails groupDetails) {
        return groups.stream().map(GroupTransformer::toGroupIndexDTO).toList();
    }


    
}
