package fr.bankwiz.server.unittest.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.PersonalFaker;
import fr.bankwiz.server.unittest.testhelper.mockrepository.BankAccountRepositoryMockFactory;
import fr.bankwiz.server.unittest.testhelper.mockrepository.GroupRepositoryMockFactory;
import fr.bankwiz.server.unittest.testhelper.mockrepository.GroupRightRepositoryMockFactory;
import fr.bankwiz.server.unittest.testhelper.mockrepository.UserRepositoryMockFactory;

public abstract class UnitTestBase {
    protected PersonalFaker faker;
    protected UnitTestFactory unitTestFactory;

    protected AuthenticationFacadeMockFactory authenticationFacadeMockFactory;

    protected UserRepositoryMockFactory userRepositoryMockFactory;
    protected GroupRepositoryMockFactory groupRepositoryMockFactory;
    protected GroupRightRepositoryMockFactory groupRightRepositoryMockFactory;

    protected BankAccountRepositoryMockFactory bankAccountRepositoryMockFactory;

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

        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
