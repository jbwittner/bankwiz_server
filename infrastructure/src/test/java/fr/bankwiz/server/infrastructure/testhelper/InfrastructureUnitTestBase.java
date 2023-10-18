package fr.bankwiz.server.infrastructure.testhelper;

import org.junit.jupiter.api.BeforeEach;

public abstract class InfrastructureUnitTestBase {

    protected InfrastructureFaker faker;
    protected InfrastructureUnitTestFactory factory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new InfrastructureFaker();
        this.factory = new InfrastructureUnitTestFactory(faker);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
