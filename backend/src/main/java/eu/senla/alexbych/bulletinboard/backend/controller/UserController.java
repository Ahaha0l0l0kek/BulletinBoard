package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.controller.request.ProfileEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @PatchMapping("/edit")
    public ResponseEntity<UserDTO> editProfile(@AuthenticationPrincipal UserDTO user,
                                            @RequestBody ProfileEditRequest profile) {
        userService.editProfile(user, profile);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> postsOfUser(@AuthenticationPrincipal UserDTO user) {
        List<PostDTO> posts = userService.getPostsByUserId(user.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PatchMapping("/{id}/setRating")
    public ResponseEntity<Boolean> setRating(@PathVariable long id, @RequestParam int rating){
        userService.setRating(id, rating);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
