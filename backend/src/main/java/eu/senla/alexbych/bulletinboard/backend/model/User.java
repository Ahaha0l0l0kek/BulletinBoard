package eu.senla.alexbych.bulletinboard.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "rating")
    private float rating;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rating> ratings;
}
