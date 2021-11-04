package eu.senla.alexbych.bulletinboard.backend.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateCommentRequest {
    @NonNull
    String comment;
}
