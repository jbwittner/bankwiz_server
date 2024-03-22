package fr.bankwiz.server.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.bankwiz.server.domain.model.User;

@SpringBootApplication
public class Infrastructure {

    public static void main(final String[] args) {
        final User user = new User(null, null, null);
        System.out.println(user);
        SpringApplication.run(Infrastructure.class, args);
    }
}
