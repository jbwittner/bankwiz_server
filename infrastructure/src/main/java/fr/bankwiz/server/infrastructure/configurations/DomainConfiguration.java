package fr.bankwiz.server.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.service.UserService;
import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;

@Configuration
public class DomainConfiguration {

    @Bean
    UserApi userService(UserSpiImpl userSpiImpl) {
        return new UserService(userSpiImpl);
    }
}
