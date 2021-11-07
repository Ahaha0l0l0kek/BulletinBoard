package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;

import java.time.LocalDateTime;

public class PostPrototype {
    public static Post aPost(){
        Post post = new Post();
        post.setId(1);
        post.setTitle("Sofa");
        post.setPrice(2300);
        post.setPicture("sofa.png");
        post.setDescription("nice sofa");
        post.setPostTime(LocalDateTime.of(2020, 3, 3, 3, 3));
        post.setPriority(false);
        post.setActive(true);
        post.setCategoryId(1);
        post.setUser(UserPrototype.aUser());
        post.setComments(null);
        return post;
    }

    public static PostDTO aPostDTO(){
        PostDTO post = new PostDTO();
        post.setId(1);
        post.setTitle("Sofa");
        post.setPrice(2300);
        post.setPicture("sofa.png");
        post.setDescription("nice sofa");
        post.setPostTime(LocalDateTime.of(2020, 3, 3, 3, 3));
        post.setPriority(false);
        post.setActive(true);
        post.setCategoryId(1);
        post.setUser(UserPrototype.aUser());
        post.setComments(null);
        return post;
    }
}
