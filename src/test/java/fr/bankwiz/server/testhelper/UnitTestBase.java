package fr.bankwiz.server.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.testhelper.mockrepository.BankAccountRepositoryMockFactory;
import fr.bankwiz.server.testhelper.mockrepository.GroupRepositoryMockFactory;
import fr.bankwiz.server.testhelper.mockrepository.GroupRightRepositoryMockFactory;
import fr.bankwiz.server.testhelper.mockrepository.TransactionRepositoryMockFactory;
import fr.bankwiz.server.testhelper.mockrepository.UserRepositoryMockFactory;

public abstract class UnitTestBase {
    protected PersonalFaker faker;
    protected UnitTestFactory unitTestFactory;

    protected AuthenticationFacadeMockFactory authenticationFacadeMockFactory;

    protected UserRepositoryMockFactory userRepositoryMockFactory;
    protected GroupRepositoryMockFactory groupRepositoryMockFactory;
    protected GroupRightRepositoryMockFactory groupRightRepositoryMockFactory;

    protected BankAccountRepositoryMockFactory bankAccountRepositoryMockFactory;

    protected TransactionRepositoryMockFactory transactionRepositoryMockFactory;

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalFaker();
        this.unitTestFactory = new UnitTestFactory(this.faker);
        this.authenticationFacadeMockFactory = new AuthenticationFacadeMockFactory();
        this.userRepositoryMockFactory = new UserRepositoryMockFactory();
        this.groupRepositoryMockFactory = new GroupRepositoryMockFactory();
        this.groupRightRepositoryMockFactory = new GroupRightRepositoryMockFactory();
        this.bankAccountRepositoryMockFactory = new BankAccountRepositoryMockFactory();
        this.transactionRepositoryMockFactory = new TransactionRepositoryMockFactory();

        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
