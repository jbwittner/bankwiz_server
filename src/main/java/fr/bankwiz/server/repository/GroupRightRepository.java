package fr.bankwiz.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;

public interface GroupRightRepository extends JpaRepository<GroupRight, Integer> {
    List<GroupRight> findAllByGroup(Group group);
}
