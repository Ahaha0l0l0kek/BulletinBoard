package eu.senla.alexbych.bulletinboard.chat.dto;

import eu.senla.alexbych.bulletinboard.chat.model.ChatRole;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
public class MessageDTO {

    private long id;
    private ChatRole chatRole;
    private String message;
    private LocalDateTime timeOfMessage;
}
