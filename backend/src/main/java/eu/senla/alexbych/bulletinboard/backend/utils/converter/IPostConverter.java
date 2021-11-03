package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import org.mapstruct.Mapper;

@Mapper
public interface IPostConverter {
    Post convertPostDTOToPost(PostDTO postDTO);
    PostDTO convertPostToPostDTO(Post post);
}
