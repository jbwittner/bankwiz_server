package fr.bankwiz.server.unittest.config;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.server.config.AudienceValidator;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

public class AudienceValidatorTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void audienceValid() {

        final String audience = "custom_audience";

        final List<String> audienceList = new ArrayList<>();
        audienceList.add(audience);

        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user")
                .audience(audienceList)
                .build();

        final AudienceValidator validator = new AudienceValidator(audience);
        final var result = validator.validate(jwt);

        Assertions.assertFalse(result.hasErrors());
    }

    @Test
    void audienceInvalid() {

        final String audience = "custom_audience";

        final List<String> audienceList = new ArrayList<>();
        audienceList.add("bad_audience");

        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user")
                .audience(audienceList)
                .build();

        final AudienceValidator validator = new AudienceValidator(audience);
        final var result = validator.validate(jwt);

        Assertions.assertTrue(result.hasErrors());
    }
}
