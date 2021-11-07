package eu.senla.alexbych.bulletinboard.chat.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatUserDTO {

    private long id;
    private String name;
}
