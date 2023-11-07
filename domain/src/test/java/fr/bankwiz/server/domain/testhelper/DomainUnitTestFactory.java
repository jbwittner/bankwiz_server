package fr.bankwiz.server.domain.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;

public class DomainUnitTestFactory {

    private DomainFaker domainFaker;

    public DomainUnitTestFactory(DomainFaker domainFaker) {
        this.domainFaker = domainFaker;
    }

    public String getAuthId() {
        return "auth|" + this.domainFaker.random().nextInt(Integer.MAX_VALUE);
    }

    public UserAuthentication getUserAuthentication() {
        return UserAuthentication.builder()
                .email(this.domainFaker.internet().emailAddress())
                .sub(this.getAuthId())
                .build();
    }

    public User getUser() {
        return User.builder()
                .authId(this.getAuthId())
                .email(this.domainFaker.internet().emailAddress())
                .userId(UUID.randomUUID())
                .build();
    }

    public Group getGroup() {
        return Group.builder()
                .groupId(UUID.randomUUID())
                .groupName(this.domainFaker.space().galaxy())
                .build();
    }

    public GroupRight getGroupRight(final Group group, final User user, final GroupRightEnum groupRightEnum) {
        return GroupRight.builder()
                .groupRightId(UUID.randomUUID())
                .group(group)
                .user(user)
                .groupRightEnum(groupRightEnum)
                .build();
    }

    public GroupRight getGroupRight(final User user, final GroupRightEnum groupRightEnum) {
        return GroupRight.builder()
                .groupRightId(UUID.randomUUID())
                .group(this.getGroup())
                .user(user)
                .groupRightEnum(groupRightEnum)
                .build();
    }
}
