package eu.senla.alexbych.bulletinboard.chat.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ChatRole chatRole;

    @Column(name = "message_text")
    private String message;

    @Column(name = "time_of_message")
    private LocalDateTime timeOfMessage;

    @JoinColumn(name = "chat_id")
    private long chatId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatRole getRole() {
        return chatRole;
    }

    public void setRole(ChatRole chatRole) {
        this.chatRole = chatRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeOfMessage() {
        return timeOfMessage;
    }

    public void setTimeOfMessage(LocalDateTime timeOfMessage) {
        this.timeOfMessage = timeOfMessage;
    }

    public ChatRole getChatRole() {
        return chatRole;
    }

    public void setChatRole(ChatRole chatRole) {
        this.chatRole = chatRole;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
