package fr.bankwiz.server.infrastructure.testhelper;

import com.github.javafaker.Faker;

import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;

public class InfrastructureFaker extends Faker {
    DomainFaker domainFaker = new DomainFaker();
}
