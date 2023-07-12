package fr.bankwiz.server.repository;

import fr.bankwiz.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAuthId(String authId);
}