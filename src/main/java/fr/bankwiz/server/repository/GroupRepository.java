package fr.bankwiz.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}