package eu.senla.alexbych.bulletinboard.backend.controller.request;

import eu.senla.alexbych.bulletinboard.backend.model.Category;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreatePostRequest {
    @NonNull
    private String title;

    private float price;

    @NonNull
    private String picture;

    @NonNull
    private String description;

    @NonNull
    private boolean priority;

    @NonNull
    private Category category;
}
