package fr.bankwiz.server.domain.testhelper.tools;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;

public class DomainUnitTestFactory {

    protected DomainFaker faker;

    public void setFaker(DomainFaker faker) {
        this.faker = faker;
    }

    public DomainUnitTestFactory() {}

    public DomainUnitTestFactory(DomainFaker faker) {
        this.faker = faker;
    }

    public String getAuthId() {
        return "auth|" + this.faker.random().nextInt(Integer.MAX_VALUE);
    }

    public UserAuthentication getUserAuthentication() {
        return UserAuthentication.builder()
                .email(this.faker.internet().emailAddress())
                .sub(this.getAuthId())
                .build();
    }

    public User getUser() {
        return User.builder()
                .authId(this.getAuthId())
                .email(this.faker.internet().emailAddress())
                .id(UUID.randomUUID())
                .build();
    }

    public Group getGroup() {
        return Group.builder()
                .id(UUID.randomUUID())
                .groupName(this.faker.space().galaxy())
                .build();
    }

    public GroupRight getGroupRight(final Group group, final User user, final GroupRightEnum groupRightEnum) {
        return GroupRight.builder()
                .id(UUID.randomUUID())
                .group(group)
                .user(user)
                .groupRightEnum(groupRightEnum)
                .build();
    }

    public GroupRight getGroupRight(final User user, final GroupRightEnum groupRightEnum) {
        return this.getGroupRight(this.getGroup(), user, groupRightEnum);
    }

    public GroupRight getGroupRight(final GroupRightEnum groupRightEnum) {
        return this.getGroupRight(this.getGroup(), this.getUser(), groupRightEnum);
    }

    public BankAccount getBankAccount(Group group) {
        return BankAccount.builder()
                .bankAccountName(this.faker.superhero().name())
                .decimalBaseAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .id(UUID.randomUUID())
                .group(group)
                .build();
    }

    public BankAccount getBankAccount() {
        final Group group = this.getGroup();
        return this.getBankAccount(group);
    }

    public Transaction getTransaction() {
        final BankAccount bankAccount = this.getBankAccount();
        return Transaction.builder().bankAccount(bankAccount).id(UUID.randomUUID()).comment(this.faker.yoda().quote()).decimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE)).build();
    }
}
