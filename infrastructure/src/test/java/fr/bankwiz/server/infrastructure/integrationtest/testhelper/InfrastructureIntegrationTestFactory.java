package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;
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
    public UserDomain getUser() {
        final UserDomain user = super.getUser();
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        this.userEntityRepository.save(userEntity);
        return user;
    }

    @Override
    public GroupDomain getGroup() {
        final GroupDomain group = super.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);
        this.groupEntityRepository.save(groupEntity);
        return group;
    }

    @Override
    public GroupRightDomain getGroupRight(
            final GroupDomain group, final UserDomain user, final GroupRightEnum groupRightEnum) {
        final GroupRightDomain groupRight = super.getGroupRight(group, user, groupRightEnum);
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        this.groupRightEntityRepository.save(groupRightEntity);
        return groupRight;
    }

    @Override
    public GroupRightDomain getGroupRight(UserDomain user, GroupRightEnum groupRightEnum) {
        final GroupRightDomain groupRight = super.getGroupRight(user, groupRightEnum);
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(groupRight.getGroup());
        final GroupRightEntity groupRightEntity = GroupRightTransformer.toGroupRightEntity(groupRight);
        this.groupEntityRepository.save(groupEntity);
        this.groupRightEntityRepository.save(groupRightEntity);
        return groupRight;
    }

    @Override
    public BankAccountDomain getBankAccount(final GroupDomain group) {
        final BankAccountDomain bankAccount = super.getBankAccount(group);
        final BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(bankAccount);
        this.bankAccountEntityRepository.save(bankAccountEntity);
        return bankAccount;
    }

    @Override
    public BankAccountDomain getBankAccount() {
        final GroupDomain group = this.getGroup();
        return this.getBankAccount(group);
    }

    @Override
    public TransactionDomain getTransaction(BankAccountDomain bankAccount) {
        final TransactionDomain transaction = super.getTransaction(bankAccount);
        final TransactionEntity transactionEntity = TransactionTransformer.toTransactionEntity(transaction);
        this.transactionEntityRepository.save(transactionEntity);
        return transaction;
    }
}
