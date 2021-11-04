package eu.senla.alexbych.bulletinboard.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id")
    private long userId;

    @JoinColumn(name = "post_id")
    private long postId;

    @Column(name = "comment_time")
    private LocalDateTime commentTime;

    @Column(name = "comment")
    private String commentText;
}
