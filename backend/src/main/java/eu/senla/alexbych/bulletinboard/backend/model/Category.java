package eu.senla.alexbych.bulletinboard.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String categoryName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryId")
    private Set<Post> posts;
}
