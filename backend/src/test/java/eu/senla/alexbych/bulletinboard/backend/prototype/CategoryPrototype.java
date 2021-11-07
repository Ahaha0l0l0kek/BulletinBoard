package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.model.Category;
import eu.senla.alexbych.bulletinboard.backend.model.Post;

import java.util.Set;

import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPost;

public class CategoryPrototype {
    public static Category aCategory(){
        Post post1 = aPost();
        Post post2 = aPost();
        post2.setId(2);
        Post post3 = aPost();
        post3.setPriority(true);
        Post post4 = aPost();
        post4.setTitle("other sofa");
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("furniture");
        category.setPosts(Set.of(post1, post2, post3, post4));
        return category;
    }
}
