package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateCommentRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.service.PostService;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Edit post")
    @PatchMapping("/{id}/edit")
    public ResponseEntity<String> editPost(@AuthenticationPrincipal UserDTO user, @PathVariable long id,
                                           @RequestBody PostEditRequest request) {
        if(postService.editPost(user, id, request) != null)
            return ResponseEntity.status(HttpStatus.OK).body("edited successfully");
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not authorized to this post");
    }

    @PatchMapping("/{id}/boost")
    public ResponseEntity<Void> boostPostPriority(@PathVariable long id) {
        postService.boostPostPriority(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> findByCategory(@PathVariable String category) {
        return new ResponseEntity<>(postService.getPostsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(@RequestParam(required = false) String order) {
        if(order == null) return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
        else return new ResponseEntity<>(postService.getAllPostsWithOrder(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/deleteMy")
    public ResponseEntity<Boolean> deleteMyPost(@AuthenticationPrincipal UserDTO user, @PathVariable long id) {
        if(postService.getPostById(id).getUser().getId() == user.getId()) {
            postService.deletePost(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@AuthenticationPrincipal UserDTO user, @RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(postService.createPost(request, user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDTO> createComment(@AuthenticationPrincipal UserDTO user,
                                                    @PathVariable long id, @RequestBody CreateCommentRequest request){
        return new ResponseEntity<>(postService.createComment(user, id, request), HttpStatus.OK);
    }

    @PostMapping("/{id}/chat/create")
    public ResponseEntity<ChatDTO> createChatWithSeller(@AuthenticationPrincipal UserDTO user, @PathVariable long id){
        ChatDTO chat = postService.createChatWithSeller(id, user.getFirstname());
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchPosts(@RequestParam String q,
                                              @RequestParam(required = false) String order,
                                              @RequestParam(required = false, defaultValue = "0") Float minPrice,
                                              @RequestParam(required = false, defaultValue = "0") Float maxPrice){
        if(order == null) return new ResponseEntity<>(postService.searchPosts(q, minPrice, maxPrice), HttpStatus.OK);
        else return new ResponseEntity<>(postService.searchPostsWithOrder(q, order, minPrice, maxPrice), HttpStatus.OK);
    }
}
