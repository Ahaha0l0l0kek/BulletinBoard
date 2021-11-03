package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
