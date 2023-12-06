package fr.bankwiz.server.infrastructure.unittest.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.infrastructure.spi.BankAccountSpiImpl;
import fr.bankwiz.server.infrastructure.spi.GroupRightSpiImpl;
import fr.bankwiz.server.infrastructure.spi.GroupSpiImpl;
import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.BankAccountRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.GroupEntityRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.GroupRightEntityRepositoryMockFactory;
import fr.bankwiz.server.infrastructure.unittest.testhelper.mock.repository.UserEntityRepositoryMockFactory;

public abstract class InfrastructureUnitTestBase {

    protected DomainFaker faker;
    protected InfrastructureUnitTestFactory factory;
    protected GroupRightEntityRepositoryMockFactory groupRightEntityRepositoryMockFactory;
    protected GroupEntityRepositoryMockFactory groupEntityRepositoryMockFactory;
    protected UserEntityRepositoryMockFactory userEntityRepositoryMockFactory;
    protected BankAccountRepositoryMockFactory bankAccountRepositoryMockFactory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new InfrastructureUnitTestFactory(faker);
        this.groupRightEntityRepositoryMockFactory = new GroupRightEntityRepositoryMockFactory();
        this.groupEntityRepositoryMockFactory = new GroupEntityRepositoryMockFactory();
        this.userEntityRepositoryMockFactory = new UserEntityRepositoryMockFactory();
        this.bankAccountRepositoryMockFactory = new BankAccountRepositoryMockFactory();
        this.initDataBeforeEach();
    }

    protected GroupRightSpiImpl buildGroupRightSpiImpl() {
        return new GroupRightSpiImpl(this.groupRightEntityRepositoryMockFactory.getRepository());
    }

    protected GroupSpiImpl buildGroupSpiImpl() {
        return new GroupSpiImpl(groupEntityRepositoryMockFactory.getRepository());
    }

    protected UserSpiImpl buildUserSpiImpl() {
        return new UserSpiImpl(userEntityRepositoryMockFactory.getRepository());
    }

    protected BankAccountSpiImpl buildBankAccountSpiImpl() {
        return new BankAccountSpiImpl(bankAccountRepositoryMockFactory.getRepository());
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
