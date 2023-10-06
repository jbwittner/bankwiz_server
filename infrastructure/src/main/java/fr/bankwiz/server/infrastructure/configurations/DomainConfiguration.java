package fr.bankwiz.server.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import ddd.DomainService;
import fr.bankwiz.server.domain.api.UserService;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.service.UserServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = {User.class},
includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})})
public class DomainConfiguration {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    
}
