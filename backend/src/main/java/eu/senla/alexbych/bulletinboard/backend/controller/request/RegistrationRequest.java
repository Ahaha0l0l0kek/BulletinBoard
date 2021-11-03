package eu.senla.alexbych.bulletinboard.backend.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegistrationRequest {
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String phoneNumber;
}
