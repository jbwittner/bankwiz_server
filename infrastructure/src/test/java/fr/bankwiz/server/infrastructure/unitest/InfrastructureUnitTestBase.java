package fr.bankwiz.server.infrastructure.unitest;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.infrastructure.testtools.InfrastructureFaker;
import fr.bankwiz.server.infrastructure.testtools.InfrastructureUnitTestFactory;

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
