package fr.bankwiz.server;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;

public abstract class TestFactory {

    protected PersonalFaker faker;

    public void setFaker(final PersonalFaker faker) {
        this.faker = faker;
    }

    protected User getUser() {

        final String authId = "auth|" + this.faker.random().nextInt(Integer.MAX_VALUE);
        return User.builder()
                .email(this.faker.internet().emailAddress())
                .firstName(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .authId(authId)
                .build();
    }

    protected Group getGroup() {
        return Group.builder().groupName(this.faker.zelda().character()).build();
    }

    protected GroupRight getGroupRight(User user, Group group, GroupRightEnum groupRightEnum) {
        final GroupRight groupRight = GroupRight.builder()
                .user(user)
                .group(group)
                .groupRightEnum(groupRightEnum)
                .build();
        user.addGroupRight(groupRight);
        group.addGroupRight(groupRight);
        return groupRight;
    }

}
