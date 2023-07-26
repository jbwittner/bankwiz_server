package fr.bankwiz.server.repository;

import fr.bankwiz.server.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.GroupRight;

import java.util.List;

public interface GroupRightRepository extends JpaRepository<GroupRight, Integer> {
    List<GroupRight> findAllByGroup(Group group);
}
