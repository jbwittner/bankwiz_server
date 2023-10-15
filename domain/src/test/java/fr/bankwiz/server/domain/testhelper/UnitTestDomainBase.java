package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.mock.MockAuthenticationSpi;
import fr.bankwiz.server.domain.testhelper.mock.MockUserSpi;

public abstract class UnitTestDomainBase {

    protected PersonalDomainFaker faker;
    protected UnitTestDomainFactory factory;
    protected MockAuthenticationSpi mockAuthenticationSpi;
    protected MockUserSpi mockUserSpi;

    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalDomainFaker();
        this.factory = new UnitTestDomainFactory(faker);
        this.mockAuthenticationSpi = new MockAuthenticationSpi();
        this.mockUserSpi = new MockUserSpi();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
