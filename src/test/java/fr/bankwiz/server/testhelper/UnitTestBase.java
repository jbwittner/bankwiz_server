package fr.bankwiz.server.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.testhelper.mockrepository.UserRepositoryMockFactory;

public abstract class UnitTestBase {
    protected PersonalFaker faker;
    protected UnitTestFactory unitTestFactory;

    protected AuthenticationFacadeMockFactory authenticationFacadeMockFactory;

    protected UserRepositoryMockFactory userRepositoryMockFactory;

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalFaker();
        this.unitTestFactory = new UnitTestFactory(this.faker);
        this.authenticationFacadeMockFactory = new AuthenticationFacadeMockFactory();
        this.userRepositoryMockFactory = new UserRepositoryMockFactory();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
