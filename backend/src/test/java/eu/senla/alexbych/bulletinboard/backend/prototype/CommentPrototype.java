package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Comment;

import java.time.LocalDateTime;

public class CommentPrototype {
    public static Comment aComment(){
        Comment comment = new Comment();
        comment.setId(1);
        comment.setPostId(1);
        comment.setUserId(1);
        comment.setCommentText("super comment");
        comment.setCommentTime(LocalDateTime.of(2020, 3, 3, 3, 3));
        return comment;
    }

    public static CommentDTO aCommentDTO(){
        CommentDTO comment = new CommentDTO();
        comment.setId(1);
        comment.setPostId(1);
        comment.setUserId(1);
        comment.setCommentText("super comment");
        comment.setCommentTime(LocalDateTime.of(2020, 3, 3, 3, 3));
        return comment;
    }
}
