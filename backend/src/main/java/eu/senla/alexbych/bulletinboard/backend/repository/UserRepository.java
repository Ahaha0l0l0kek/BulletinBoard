package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
