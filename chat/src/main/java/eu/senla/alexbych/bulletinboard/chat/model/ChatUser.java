package eu.senla.alexbych.bulletinboard.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "users_chat")
public class ChatUser {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    private String name;
}
