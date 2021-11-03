package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.chat.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends JpaRepository <ChatUser, Long>{
}
