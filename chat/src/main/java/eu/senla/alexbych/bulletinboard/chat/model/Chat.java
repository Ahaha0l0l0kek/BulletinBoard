package eu.senla.alexbych.bulletinboard.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private ChatUser seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private ChatUser buyer;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;
}
