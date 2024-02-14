package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.tools.DomainUnitTestFactory;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Component
public class InfrastructureIntegrationTestFactory extends DomainUnitTestFactory {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private GroupEntityRepository groupEntityRepository;

    @Autowired
    private GroupRightEntityRepository groupRightEntityRepository;

    @Autowired
    private BankAccountEntityRepository bankAccountEntityRepository;

    @Autowired
    private TransactionEntityRepository transactionEntityRepository;

    public InfrastructureIntegrationTestFactory() {}

    @Override
    public User getUser() {
        final User user = super.getUser();
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        this.userEntityRepository.save(userEntity);
        return user;
    }

    @Override
    public Group getGroup() {
        final Group group = super.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        this.groupEntityRepository.save(groupEntity);
        return group;
    }

    @Override
    public GroupRight getGroupRight(final Group group, final User user, final GroupRightEnum groupRightEnum) {
        final GroupRight groupRight = super.getGroupRight(group, user, groupRightEnum);
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        this.groupRightEntityRepository.save(groupRightEntity);
        return groupRight;
    }

    @Override
    public GroupRight getGroupRight(User user, GroupRightEnum groupRightEnum) {
        final GroupRight groupRight = super.getGroupRight(user, groupRightEnum);
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(groupRight.getGroup());
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        this.groupEntityRepository.save(groupEntity);
        this.groupRightEntityRepository.save(groupRightEntity);
        return groupRight;
    }

    @Override
    public BankAccount getBankAccount(final Group group) {
        final BankAccount bankAccount = super.getBankAccount(group);
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);
        this.bankAccountEntityRepository.save(bankAccountEntity);
        return bankAccount;
    }

    @Override
    public BankAccount getBankAccount() {
        final Group group = this.getGroup();
        return this.getBankAccount(group);
    }

    @Override
    public Transaction getTransaction(BankAccount bankAccount) {
        final Transaction transaction = super.getTransaction(bankAccount);
        final TransactionEntity transactionEntity = TransactionTransformer.toTransactionEntity(transaction);
        this.transactionEntityRepository.save(transactionEntity);
        return transaction;
    }
}
