package fr.bankwiz.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAuthId(String authId);
}
