package fr.bankwiz.server.infrastructure.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public class InfrastructureUnitTestFactory {

    private InfrastructureFaker infrastructureFaker;

    public InfrastructureUnitTestFactory(InfrastructureFaker infrastructureFaker) {
        this.infrastructureFaker = infrastructureFaker;
    }

    public String getAuthId() {
        return "auth|" + this.infrastructureFaker.random().nextInt(Integer.MAX_VALUE);
    }

    public User getUser() {
        return User.builder()
                .authId(this.getAuthId())
                .email(this.infrastructureFaker.internet().emailAddress())
                .userId(UUID.randomUUID())
                .build();
    }

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .authId(this.getAuthId())
                .email(this.infrastructureFaker.internet().emailAddress())
                .userId(UUID.randomUUID())
                .build();
    }

    public Group getGroup() {
        return Group.builder()
                .groupName(this.infrastructureFaker.space().star())
                .groupId(UUID.randomUUID())
                .build();
    }

    public GroupEntity getGroupEntity() {
        return GroupEntity.builder()
                .groupName(this.infrastructureFaker.space().star())
                .groupId(UUID.randomUUID())
                .build();
    }

    public GroupRight getGroupRight(final GroupRightEnum groupRightEnum) {
        return GroupRight.builder()
                .groupRightId(UUID.randomUUID())
                .group(this.getGroup())
                .user(this.getUser())
                .groupRightEnum(groupRightEnum)
                .build();
    }

    public GroupRightEntity getGroupRightEntity(
            final GroupEntity groupEntity,
            final UserEntity userEntity,
            final GroupRightEntityEnum groupRightEntityEnum) {
        return GroupRightEntity.builder()
                .groupRightId(UUID.randomUUID())
                .groupEntity(groupEntity)
                .userEntity(userEntity)
                .groupRightEntityEnum(groupRightEntityEnum)
                .build();
    }

    public GroupRightEntity getGroupRightEntity(
            final UserEntity userEntity, final GroupRightEntityEnum groupRightEntityEnum) {
        return this.getGroupRightEntity(this.getGroupEntity(), userEntity, groupRightEntityEnum);
    }

    public GroupRightEntity getGroupRightEntity(
            final GroupEntity groupEntity, final GroupRightEntityEnum groupRightEntityEnum) {
        return this.getGroupRightEntity(groupEntity, this.getUserEntity(), groupRightEntityEnum);
    }

    public GroupRightEntity getGroupRightEntity(final GroupRightEntityEnum groupRightEntityEnum) {
        return this.getGroupRightEntity(this.getGroupEntity(), this.getUserEntity(), groupRightEntityEnum);
    }
}
