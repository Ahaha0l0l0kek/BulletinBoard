package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateCommentRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.service.PostService;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PatchMapping("/{id}/edit")
    public ResponseEntity<String> editPost(@AuthenticationPrincipal UserDTO user, @PathVariable long id,
                                           @RequestBody PostEditRequest request) {
        if(postService.editPost(user, id, request))
            return ResponseEntity.ok().build() ;
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not authorized to this post");
    }

    @PatchMapping("/boost/{id}")
    public ResponseEntity<Void> boostPostPriority(@PathVariable long id) {
        postService.boostPostPriority(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> findByCategory(@PathVariable String category, @RequestParam String order) {
        if(order.isEmpty()) return new ResponseEntity<>(postService.getPostsByCategory(category), HttpStatus.OK);
        else return new ResponseEntity<>(postService.findPostsByCategoryWithOrder(category, order), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(@RequestParam String order) {
        if(order.isEmpty()) return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
        else return new ResponseEntity<>(postService.getAllPostsWithOrder(order), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMy/{id}")
    public ResponseEntity<Boolean> deleteMyPost(@AuthenticationPrincipal UserDTO user, @PathVariable long id) {
        if(postService.getPostById(id).getUser().getId() == user.getId()) {
            postService.deletePost(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<CommentDTO> createComment(@AuthenticationPrincipal UserDTO user,
                                                    @PathVariable long id, @RequestBody CreateCommentRequest request){
        return new ResponseEntity<>(postService.createComment(user, id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/chat/create")
    public ResponseEntity<ChatDTO> createChatWithSeller(@AuthenticationPrincipal UserDTO user, @PathVariable long id){
        ChatDTO chat = postService.createChatWithSeller(id, user.getFirstname());
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchPosts(@RequestParam String search, @RequestParam String order){
        if(order.isEmpty()) return new ResponseEntity<>(postService.searchPosts(search), HttpStatus.OK);
        else return new ResponseEntity<>(postService.searchPostsWithOrder(search, order), HttpStatus.OK);
    }
}
