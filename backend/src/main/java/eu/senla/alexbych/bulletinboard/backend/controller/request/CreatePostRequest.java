package eu.senla.alexbych.bulletinboard.backend.controller.request;

import eu.senla.alexbych.bulletinboard.backend.model.Category;
import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private float price;
    private String picture;
    private String description;
    private boolean priority;
    private Category category;
}
