package fr.bankwiz.server.infrastructure.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

@Component
public class UserSpiImpl implements UserSpi {

    private Map<String, User> userMap = new HashMap<>();

    @Override
    public Optional<User> findByAuthId(String authId) {
        final User user = userMap.get(authId);
        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        userMap.put(user.getAuthId(), user);
        return user;
    }
}
