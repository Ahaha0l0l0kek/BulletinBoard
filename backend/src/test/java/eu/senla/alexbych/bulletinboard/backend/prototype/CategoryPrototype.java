package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.model.Category;

public class CategoryPrototype {
    public static Category aCategory(){
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("furniture");
        return category;
    }
}
