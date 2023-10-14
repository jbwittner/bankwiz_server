package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationFacadeSpi;

@Component
public class AuthenticationFacadeSpiImpl implements AuthenticationFacadeSpi {

    @Override
    public UserAuthentication getUserAuthentication() {
        return UserAuthentication.builder()
                .email("toto@email.com")
                .sub("suuuuuub")
                .name("thisisname")
                .build();
    }
}
