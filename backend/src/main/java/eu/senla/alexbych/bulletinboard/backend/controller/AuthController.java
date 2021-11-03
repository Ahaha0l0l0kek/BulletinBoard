package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.service.UserService;
import eu.senla.alexbych.bulletinboard.backend.config.jwt.JwtProvider;
import eu.senla.alexbych.bulletinboard.backend.controller.request.AuthRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.RegistrationRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.response.AuthResponse;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationRequest request) {
        User u = new User();
        u.setFirstname(request.getFirstname());
        u.setLastname(request.getLastname());
        u.setPhoneNumber(request.getPhoneNumber());
        u.setPassword(request.getPassword());
        u.setLogin(request.getLogin());
        return new ResponseEntity<>(userService.saveUser(u), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

    @PatchMapping("/setAdmin")
    public ResponseEntity<Void> setAdmin(@RequestParam String login) {
        userService.update(login);
        return ResponseEntity.ok().build();
    }
}
