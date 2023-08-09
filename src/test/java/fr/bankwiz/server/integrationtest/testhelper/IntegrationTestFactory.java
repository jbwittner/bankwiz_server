package fr.bankwiz.server.integrationtest.testhelper;

import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class IntegrationTestFactory extends TestFactory {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupRightRepository groupRightRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public User getUser() {
        final User user = super.getUser();
        return this.userRepository.save(user);
    }

    public Group getGroup() {
        Group group = super.getGroup();
        return this.groupRepository.save(group);
    }

    public void addUserToGroup(User user, Group group, GroupRightEnum groupRightEnum) {
        GroupRight groupRight = super.getGroupRight(user, group, groupRightEnum);
        this.groupRightRepository.save(groupRight);
    }

    public Group getGroupWithRight(User userToAdd, GroupRightEnum groupRightEnum) {
        final Group group = this.getGroup();
        this.addUserToGroup(userToAdd, group, groupRightEnum);
        return group;
    }

    public BankAccount getBankAccount(Integer baseAmountDecimal, String name, Group group) {
        BankAccount bankAccount = super.getBankAccount(baseAmountDecimal, name, group);
        bankAccount = this.bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public BankAccount getBankAccount(Group group) {
        BankAccount bankAccount = super.getBankAccount(
                this.faker.random().nextInt(Integer.MAX_VALUE),
                this.faker.leagueOfLegends().champion(),
                group);
        bankAccount = this.bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public Transaction getTransaction(BankAccount bankAccount) {
        Transaction transaction = super.getTransaction(
                faker.random().nextInt(Integer.MAX_VALUE),
                faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                bankAccount);
        transaction = this.transactionRepository.save(transaction);
        return transaction;
    }
}
