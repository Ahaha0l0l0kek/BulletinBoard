package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PostConverter {
    public Post convertPostDTOToPost(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setPrice(postDTO.getPrice());
        post.setPicture(postDTO.getPicture());
        post.setDescription(postDTO.getDescription());
        post.setPostTime(postDTO.getPostTime());
        post.setPriority(postDTO.isPriority());
        post.setActive(postDTO.isActive());
        post.setCategory(postDTO.getCategory());
        post.setUser(postDTO.getUser());
        post.setComments(postDTO.getComments());
        return post;
    }
    public PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setPrice(post.getPrice());
        postDTO.setPicture(post.getPicture());
        postDTO.setDescription(post.getDescription());
        postDTO.setPostTime(post.getPostTime());
        postDTO.setPriority(post.isPriority());
        postDTO.setActive(post.isActive());
        postDTO.setCategory(post.getCategory());
        postDTO.setUser(post.getUser());
        postDTO.setComments(post.getComments());
        return postDTO;
    }
}
