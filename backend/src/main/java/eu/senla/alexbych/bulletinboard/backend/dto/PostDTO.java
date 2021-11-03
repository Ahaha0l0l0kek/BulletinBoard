package eu.senla.alexbych.bulletinboard.backend.dto;

import eu.senla.alexbych.bulletinboard.backend.model.Category;
import eu.senla.alexbych.bulletinboard.backend.model.Comment;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import lombok.*;

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
    private Category category;
    private User user;
    private Set<Comment> comments;
}
