package fr.bankwiz.server.infrastructure.testhelper;

import fr.bankwiz.server.domain.testhelper.UnitTestDomainFactory;

public class UnitTestInfrastructureFactory {

    private PersonalInfrastructureFaker infrastructureFaker;
    private UnitTestDomainFactory domainFactory;

    public UnitTestInfrastructureFactory(PersonalInfrastructureFaker infrastructureFaker, UnitTestDomainFactory domainFactory) {
        this.infrastructureFaker = infrastructureFaker;
        this.domainFactory = domainFactory;
    }

}
