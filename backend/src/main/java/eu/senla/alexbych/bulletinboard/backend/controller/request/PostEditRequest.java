package eu.senla.alexbych.bulletinboard.backend.controller.request;

import lombok.Data;

@Data
public class PostEditRequest {
    private String title;
    private float price;
    private String picture;
    private String description;
}
