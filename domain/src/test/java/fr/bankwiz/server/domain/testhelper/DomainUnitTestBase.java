package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.mock.DomainMockAuthenticationSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockUserSpi;

public abstract class DomainUnitTestBase {

    protected DomainFaker faker;
    protected DomainUnitTestFactory factory;
    protected DomainMockAuthenticationSpi mockAuthenticationSpi;
    protected DomainMockUserSpi mockUserSpi;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new DomainUnitTestFactory(faker);
        this.mockAuthenticationSpi = new DomainMockAuthenticationSpi();
        this.mockUserSpi = new DomainMockUserSpi();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
