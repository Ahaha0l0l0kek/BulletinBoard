package eu.senla.alexbych.bulletinboard.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Getter
@Setter
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "user_id")
    private long userId;

    @Column(name = "rate")
    @Max(5)
    private int rate;
}
