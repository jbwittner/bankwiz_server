package fr.bankwiz.server.infrastructure;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.service.HelperDomain;

public class TestInfra {

    private HelperDomain helperDomain;

    @Test
    void testToto() {
        final UserDomain testtatot = new UserDomain(UUID.randomUUID(), "", "");
        privateFunction(testtatot);
        Assertions.assertNotNull(testtatot);
        Assertions.assertTrue(true);
    }

    private void privateFunction(final UserDomain totoDomain) {
        System.out.println(totoDomain);
    }
}
