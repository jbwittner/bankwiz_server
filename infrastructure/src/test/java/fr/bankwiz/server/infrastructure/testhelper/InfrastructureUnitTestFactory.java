package fr.bankwiz.server.infrastructure.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
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
                .userUuid(UUID.randomUUID())
                .build();
    }

    public Group getGroup() {
        return Group.builder()
                .groupName(this.infrastructureFaker.space().star())
                .groupUuid(UUID.randomUUID())
                .build();
    }

    public GroupRight getGroupEntity(final GroupRightEnum groupRightEnum) {
        return GroupRight.builder()
                .groupRightUuid(UUID.randomUUID())
                .group(this.getGroup())
                .user(this.getUser())
                .groupRightEnum(groupRightEnum)
                .build();
    }

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .authId(this.getAuthId())
                .email(this.infrastructureFaker.internet().emailAddress())
                .userId(UUID.randomUUID())
                .build();
    }
}
