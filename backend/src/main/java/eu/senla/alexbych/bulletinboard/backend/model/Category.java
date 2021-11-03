package eu.senla.alexbych.bulletinboard.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Post> posts;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Post> getBulletins() {
        return posts;
    }

    public void setBulletins(Set<Post> posts) {
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return categoryName;
    }

    public void setCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + categoryName + '\'' +
                '}';
    }
}
