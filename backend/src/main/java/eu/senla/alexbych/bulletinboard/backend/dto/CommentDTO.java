package eu.senla.alexbych.bulletinboard.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private long id;
    private long userId;
    private long postId;
    private LocalDateTime commentTime;
    private String commentText;
}
