package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface ICommentConverter {
    Comment convertCommentDTOToComment(CommentDTO commentDTO);
    CommentDTO convertCommentToCommentDTO(Comment comment);
}
