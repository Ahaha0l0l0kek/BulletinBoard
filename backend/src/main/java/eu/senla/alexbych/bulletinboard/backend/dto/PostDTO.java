package eu.senla.alexbych.bulletinboard.backend.dto;

import eu.senla.alexbych.bulletinboard.backend.model.Comment;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private long id;
    private String title;
    private float price;
    private String picture;
    private String description;
    private LocalDateTime postTime;
    private boolean priority;
    private boolean active;
    private long categoryId;
    private User user;
    private Set<Comment> comments;
}
