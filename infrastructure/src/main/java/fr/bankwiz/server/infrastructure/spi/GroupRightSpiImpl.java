package fr.bankwiz.server.infrastructure.spi;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

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

    @Override
    public List<GroupRight> findByUser(User user) {
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        final List<GroupRightEntity> groupRightEntities = this.groupRightEntityRepository.findByUserEntity(userEntity);
        return GroupRightTransformer.fromGroupRightEntity(groupRightEntities);
    }

    @Override
    public List<GroupRight> findByGroup(Group group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        final List<GroupRightEntity> groupRightEntities =
                this.groupRightEntityRepository.findByGroupEntity(groupEntity);
        return GroupRightTransformer.fromGroupRightEntity(groupRightEntities);
    }

    @Override
    public void deleteByGroupAndUser(Group group, User user) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        this.groupRightEntityRepository.deleteByGroupEntityAndUserEntity(groupEntity, userEntity);
    }

    @Override
    public void deleteAllByGroup(Group group) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        this.groupRightEntityRepository.deleteAllByGroupEntity(groupEntity);
    }
}
