package fr.bankwiz.server.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.TotoDomain;
import fr.bankwiz.server.domain.service.HelperDomain;

public class TestInfra {

    private HelperDomain helperDomain;
    private TotoDomain totoDomain;

    @Test
    void testToto() {
        final TotoDomain testtatot = new TotoDomain();
        privateFunction(testtatot);
        Assertions.assertNotNull(testtatot);
        Assertions.assertTrue(true);
    }

    private void privateFunction(final TotoDomain totoDomain) {
        System.out.println(totoDomain);
    }
}
