package fr.bankwiz.server.infrastructure.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.infrastructure.testhelper.mock.MockUserSpi;

public abstract class InfrastructureUnitTestBase {

    protected InfrastructureFaker faker;
    protected InfrastructureUnitTestFactory factory;
    protected MockUserSpi mockUserSpi;

    @BeforeEach
    public void beforeEach() {
        this.faker = new InfrastructureFaker();
        this.factory = new InfrastructureUnitTestFactory(faker);
        this.mockUserSpi = new MockUserSpi();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
