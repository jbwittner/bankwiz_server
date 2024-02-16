package fr.bankwiz.server.infrastructure.spi;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

@Component
public class GroupSpiImpl implements GroupSpi {

    private GroupEntityRepository groupEntityRepository;

    public GroupSpiImpl(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    @Override
    public GroupDomain save(GroupDomain group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        final GroupEntity groupEntitySaved = this.groupEntityRepository.save(groupEntity);
        return GroupTransformer.fromGroupEntity(groupEntitySaved);
    }

    @Override
    public Optional<GroupDomain> findById(UUID id) {
        final Optional<GroupEntity> optionalGroupEntity = this.groupEntityRepository.findById(id);
        if (optionalGroupEntity.isEmpty()) {
            return Optional.empty();
        } else {
            final GroupDomain group = GroupTransformer.fromGroupEntity(optionalGroupEntity.get());
            return Optional.of(group);
        }
    }

    @Override
    public void deleteById(UUID id) {
        this.groupEntityRepository.deleteById(id);
    }
}
