package eu.senla.alexbych.bulletinboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CustomerPost {
    private long id;
    private String title;
    private float price;
    private String picture;
}
