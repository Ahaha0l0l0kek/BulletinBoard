package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.controller.request.ProfileRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @PatchMapping("/edit")
    public ResponseEntity<Void> editProfile(@RequestBody ProfileRequest profile) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        if(profile.getFirstname() != null)
            user.setFirstname(profile.getFirstname());
        if(profile.getLastname() != null)
            user.setLastname(profile.getLastname());
        if(profile.getPhoneNumber() != null)
            user.setPhoneNumber(profile.getPhoneNumber());
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> postsOfUser(@PathVariable long id) {
        List<PostDTO> posts = userService.getPostsByUserId(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PatchMapping("/{id}/setrating")
    public ResponseEntity<Boolean> setRating(@PathVariable long id, @RequestParam int rating){
        userService.setRating(id, rating);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
