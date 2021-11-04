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
    public ResponseEntity<Boolean> registerUser(@RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(userService.saveUser(request), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

    @PatchMapping("/setAdmin")
    public ResponseEntity<Boolean> setAdmin(@RequestParam String login) {
        return new ResponseEntity<>(userService.update(login), HttpStatus.OK);
    }
}
