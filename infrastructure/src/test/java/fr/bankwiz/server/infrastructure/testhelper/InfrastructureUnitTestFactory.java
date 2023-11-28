package fr.bankwiz.server.infrastructure.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.domain.testhelper.tools.DomainUnitTestFactory;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public class InfrastructureUnitTestFactory extends DomainUnitTestFactory {

    public InfrastructureUnitTestFactory(DomainFaker faker) {
        super(faker);
    }

    public String getAuthId() {
        return "auth|" + this.faker.random().nextInt(Integer.MAX_VALUE);
    }

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .authId(this.getAuthId())
                .email(this.faker.internet().emailAddress())
                .id(UUID.randomUUID())
                .build();
    }

    public GroupEntity getGroupEntity() {
        return GroupEntity.builder()
                .groupName(this.faker.space().star())
                .id(UUID.randomUUID())
                .build();
    }

    public GroupRightEntity getGroupRightEntity(
            final GroupEntity groupEntity,
            final UserEntity userEntity,
            final GroupRightEntityEnum groupRightEntityEnum) {
        return GroupRightEntity.builder()
                .id(UUID.randomUUID())
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

    public BankAccountEntity getBankAccountEntity(GroupEntity groupEntity) {
        return BankAccountEntity.builder()
                .bankAccountName(this.faker.superhero().name())
                .baseAmountDecimal(this.faker.random().nextInt(Integer.MAX_VALUE))
                .id(UUID.randomUUID())
                .groupEntity(groupEntity)
                .build();
    }
}
