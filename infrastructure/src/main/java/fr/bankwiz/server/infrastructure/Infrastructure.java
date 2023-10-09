package fr.bankwiz.server.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.bankwiz.server.domain.api.UserService;

@SpringBootApplication
public class Infrastructure {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Infrastructure.class, args);
    }
}
