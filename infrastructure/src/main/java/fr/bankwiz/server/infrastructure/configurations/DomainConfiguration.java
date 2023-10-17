package fr.bankwiz.server.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.UserSpi;

@Configuration
public class DomainConfiguration {

    @Bean
    UserApi userApi(AuthenticationSpi authenticationSpi, UserSpi userSpi) {
        return new UserDomainService(authenticationSpi, userSpi);
    }
}
