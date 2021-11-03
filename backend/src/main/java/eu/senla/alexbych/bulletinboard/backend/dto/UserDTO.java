package eu.senla.alexbych.bulletinboard.backend.dto;

import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.model.Role;
import eu.senla.alexbych.bulletinboard.backend.model.Rating;
import eu.senla.alexbych.bulletinboard.chat.model.Message;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String login;
    private String password;
    private float rating;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Role role;
    private Set<Post> posts;
    private Set<Rating> ratings;
}
