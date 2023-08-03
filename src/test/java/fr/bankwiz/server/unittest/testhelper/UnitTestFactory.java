package fr.bankwiz.server.unittest.testhelper;

import fr.bankwiz.server.PersonalFaker;
import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;

public class UnitTestFactory extends TestFactory {
    public UnitTestFactory(PersonalFaker faker) {
        super.setFaker(faker);
    }

    protected Integer getRandomId() {
        return this.faker.random().nextInt(Integer.MAX_VALUE);
    }

    public User getUser() {
        User user = super.getUser();
        user.setUserAccountId(this.getRandomId());
        return user;
    }

    public Group getGroup() {
        Group group = super.getGroup();
        group.setUserGroupId(this.getRandomId());
        return group;
    }

    public void addUserToGroup(User user, Group group, GroupRightEnum groupRightEnum) {
        this.getGroupRight(user, group, groupRightEnum);
    }

    public GroupRight getGroupRight(User user, Group group, GroupRightEnum groupRightEnum) {
        GroupRight groupRight = super.getGroupRight(user, group, groupRightEnum);
        groupRight.setRightId(getRandomId());
        return groupRight;
    }

    public Group getGroupWithRight(User userToAdd, GroupRightEnum groupRightEnum) {
        final Group group = this.getGroup();
        this.getGroupRight(userToAdd, group, groupRightEnum);
        return group;
    }

    public BankAccount getBankAccount(Integer baseAmountDecimal, String name, Group group) {
        BankAccount bankAccount = super.getBankAccount(baseAmountDecimal, name, group);
        bankAccount.setId(getRandomId());
        return bankAccount;
    }

    public BankAccount getBankAccount(Group group) {
        BankAccount bankAccount = super.getBankAccount(
                this.faker.random().nextInt(Integer.MAX_VALUE),
                this.faker.leagueOfLegends().champion(),
                group);
        bankAccount.setId(getRandomId());
        return bankAccount;
    }
}
