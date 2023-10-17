package fr.bankwiz.server.infrastructure.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.UnitTestDomainFactory;

public abstract class UnitTestInfrastructureBase {

    protected PersonalInfrastructureFaker faker;
    protected UnitTestInfrastructureFactory factory;
    private UnitTestDomainFactory unitTestDomainFactory;

    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalInfrastructureFaker();
        this.factory = new UnitTestInfrastructureFactory(faker, unitTestDomainFactory);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
