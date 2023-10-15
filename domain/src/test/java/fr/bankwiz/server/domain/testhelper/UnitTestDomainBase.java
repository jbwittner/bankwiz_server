package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

public abstract class UnitTestDomainBase {

    protected PersonalDomainFaker faker;
    protected UnitTestDomainFactory factory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalDomainFaker();
        this.factory = new UnitTestDomainFactory(faker);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
