package eu.senla.alexbych.bulletinboard.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private float price;

    @Column(name = "picture")
    private String picture;

    @Column(name = "description")
    private String description;

    @Column(name = "post_time")
    private LocalDateTime postTime;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "priority")
    private boolean priority;

    @ManyToOne
    @JoinColumn(name = "Category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany
    private Set<Comment> comments;
}
