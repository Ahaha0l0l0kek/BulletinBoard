package eu.senla.alexbych.bulletinboard.chat.model;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @OneToMany
    private List<Message> messages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatUser getSeller() {
        return seller;
    }

    public void setSeller(ChatUser seller) {
        this.seller = seller;
    }

    public ChatUser getBuyer() {
        return buyer;
    }

    public void setBuyer(ChatUser buyer) {
        this.buyer = buyer;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
