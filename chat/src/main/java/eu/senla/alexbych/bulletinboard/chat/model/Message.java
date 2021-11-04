package eu.senla.alexbych.bulletinboard.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_user_role")
    private ChatRole chatRole;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "time_of_message")
    private LocalDateTime timeOfMessage;

    @JoinColumn(name = "chat_id")
    private long chatId;
}
