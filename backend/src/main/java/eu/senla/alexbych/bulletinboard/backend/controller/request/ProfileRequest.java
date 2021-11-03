package eu.senla.alexbych.bulletinboard.backend.controller.request;

import lombok.Data;


@Data
public class ProfileRequest {
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
