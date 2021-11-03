package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateMessageRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.service.PostService;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PatchMapping("/{id}/edit")
    public ResponseEntity<String> editPost(@PathVariable long id, @RequestBody PostEditRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        PostDTO post = postService.getPostById(id);
        if(post.getUser().getId() == user.getId()) {
            if (request.getTitle() != null)
                post.setTitle(request.getTitle());
            if (request.getPicture() != null)
                post.setPicture(request.getPicture());
            if (request.getDescription() != null)
                post.setDescription(request.getDescription());
            if (request.getPrice() != -1) {
                post.setPrice(request.getPrice());
            } else post.setPrice(post.getPrice());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not authorized to this post");
    }

    @PatchMapping("/boost/{id}")
    public ResponseEntity<Void> boostPostPriority(@PathVariable long id) {
        postService.boostPostPriority(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> findByCategory(@PathVariable String category) {
        List<PostDTO> posts = postService.getPostsByCategory(category);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/categoryorder/{category}")
    public ResponseEntity<Object> findByCategoryWithOrder(@PathVariable String category, @RequestParam String order) {
        List<PostDTO> posts = postService.findPostsByCategoryWithOrder(category, order);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/allorder")
    public ResponseEntity<Object> getAllPostsWithOrder(@RequestParam String order) {
        List<PostDTO> posts = postService.getAllPostsWithOrder(order);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/deletemy/{id}")
    public ResponseEntity<Boolean> deleteMyPost(@PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        if(postService.getPostById(id).getUser().getId() == user.getId()) {
            postService.deletePost(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostRequest request) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(request.getTitle());
        postDTO.setPrice(request.getPrice());
        postDTO.setPicture(request.getPicture());
        postDTO.setDescription(request.getDescription());
        postDTO.setPostTime(LocalDateTime.now());
        postDTO.setPriority(request.isPriority());
        postDTO.setActive(true);
        postDTO.setCategory(request.getCategory());
        postDTO.setUser((User) SecurityContextHolder.getContext().getAuthentication());
        postService.createPost(postDTO);
        return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long id, @RequestBody CreateMessageRequest request){
        CommentDTO comment = new CommentDTO();
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        comment.setUserId(user.getId());
        comment.setPostId(id);
        comment.setCommentTime(LocalDateTime.now());
        comment.setComment(request.getComment());
        return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
    }

    @PutMapping("/{id}/chat/create")
    public ResponseEntity<ChatDTO> createChatWithSeller(@PathVariable long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        ChatDTO chat = postService.createChatWithSeller(id, user.getFirstname());
        return new ResponseEntity<ChatDTO>(chat, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchPosts(@RequestParam String search){
        List<PostDTO> posts = postService.searchPosts(search);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/searchorder")
    public ResponseEntity<Object> searchPostsWithOrder(@RequestParam String search, @RequestParam String order){
        List<PostDTO> posts = postService.searchPostsWithOrder(search, order);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
