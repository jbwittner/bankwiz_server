package fr.bankwiz.server.infrastructure.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;

public abstract class InfrastructureUnitTestBase {

    protected DomainFaker faker;
    protected InfrastructureUnitTestFactory factory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new InfrastructureUnitTestFactory(faker);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
