package fr.bankwiz.server.testhelper;

import java.time.LocalDate;
import java.time.ZoneId;

import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;

public class UnitTestFactory extends TestFactory {
    public UnitTestFactory(PersonalFaker faker) {
        super.setFaker(faker);
    }

    protected Integer getRandomId() {
        return this.faker.random().nextInt(Integer.MAX_VALUE);
    }

    public User getUser() {
        final String authId = "auth|" + this.faker.random().nextInt(Integer.MAX_VALUE);
        return User.builder()
                .email(this.faker.internet().emailAddress())
                .firstName(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .authId(authId)
                .userAccountId(this.getRandomId())
                .build();
    }

    public Group getGroup() {
        return Group.builder().name(this.faker.zelda().character()).userGroupId(this.getRandomId()).build();
    }

    public void addUserToGroup(User user, Group group, GroupRightEnum groupRightEnum) {
        this.getGroupRight(user, group, groupRightEnum);
    }

    public GroupRight getGroupRight(User user, Group group, GroupRightEnum groupRightEnum) {
        final GroupRight groupRight = GroupRight.builder()
                .user(user)
                .group(group)
                .groupRightEnum(groupRightEnum)
                .rightId(getRandomId())
                .build();

        user.addGroupRight(groupRight);
        group.addGroupRight(groupRight);
        return groupRight;
    }

    public Group getGroupWithRight(User userToAdd, GroupRightEnum groupRightEnum) {
        final Group group = this.getGroup();
        this.addUserToGroup(userToAdd, group, groupRightEnum);
        return group;
    }

    public BankAccount getBankAccount(Integer baseAmountDecimal, String name, Group group) {
        final BankAccount bankAccount = BankAccount.builder()
                .baseAmountDecimal(baseAmountDecimal)
                .name(name)
                .build();

        bankAccount.setId(getRandomId());

        group.addBankAccount(bankAccount);
        return bankAccount;
    }

    public BankAccount getBankAccount(Group group) {
        BankAccount bankAccount = this.getBankAccount(
                this.faker.random().nextInt(Integer.MAX_VALUE),
                this.faker.leagueOfLegends().champion(),
                group);
        bankAccount.setId(getRandomId());
        return bankAccount;
    }

    public Transaction getTransaction(Integer amount, LocalDate date, BankAccount bankAccount) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .date(date)
                .bankAccount(bankAccount)
                .build();
                transaction.setTransactionId(getRandomId());
                return transaction;

    }

    public Transaction getTransaction(BankAccount bankAccount) {
        Transaction transaction = super.getTransaction(
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date()
                        .birthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),
                bankAccount);
        return transaction;
    }
}
