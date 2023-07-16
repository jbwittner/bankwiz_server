package fr.bankwiz.server.unittest.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.PersonalFaker;
import fr.bankwiz.server.unittest.testhelper.mockrepository.UserRepositoryMockFactory;

public abstract class UnitTestBase {
    protected PersonalFaker faker;
    protected UnitTestFactory unitTestFactory;
    protected UserRepositoryMockFactory userRepositoryMockFactory;

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalFaker();
        this.unitTestFactory = new UnitTestFactory(this.faker);
        this.userRepositoryMockFactory = new UserRepositoryMockFactory();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
