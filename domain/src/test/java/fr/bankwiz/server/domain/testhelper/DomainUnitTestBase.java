package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

public abstract class DomainUnitTestBase {
    protected DomainFaker faker;
    protected DomainUnitTestFactory factory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new DomainUnitTestFactory(faker);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
