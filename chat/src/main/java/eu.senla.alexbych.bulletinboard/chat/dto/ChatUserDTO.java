package eu.senla.alexbych.bulletinboard.chat.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ChatUserDTO {

    private long id;
    private String name;
}
