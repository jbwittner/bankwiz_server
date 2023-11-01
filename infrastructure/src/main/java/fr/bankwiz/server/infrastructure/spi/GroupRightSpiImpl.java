package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;

@Component
public class GroupRightSpiImpl implements GroupRightSpi {

    private GroupRightEntityRepository groupRightEntityRepository;

    public GroupRightSpiImpl(GroupRightEntityRepository groupRightEntityRepository) {
        this.groupRightEntityRepository = groupRightEntityRepository;
    }

    @Override
    public GroupRight save(GroupRight groupRight) {
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        final GroupRightEntity groupRightEntitySaved = this.groupRightEntityRepository.save(groupRightEntity);
        return GroupRightTransformer.fromGroupRightEntity(groupRightEntitySaved);
    }
}
