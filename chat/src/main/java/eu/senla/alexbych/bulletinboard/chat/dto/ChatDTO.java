package eu.senla.alexbych.bulletinboard.chat.dto;

import eu.senla.alexbych.bulletinboard.chat.model.ChatUser;
import eu.senla.alexbych.bulletinboard.chat.model.Message;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatDTO {

    private long id;
    private ChatUser seller;
    private ChatUser buyer;
    private List<Message> messages;
}
