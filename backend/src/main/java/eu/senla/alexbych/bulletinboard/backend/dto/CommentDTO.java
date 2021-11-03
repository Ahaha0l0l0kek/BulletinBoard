package eu.senla.alexbych.bulletinboard.backend.dto;

import lombok.*;

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
    private String comment;
}
