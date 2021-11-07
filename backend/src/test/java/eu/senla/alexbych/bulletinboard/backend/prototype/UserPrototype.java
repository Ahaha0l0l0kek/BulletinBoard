package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.User;

import java.util.Set;

public class UserPrototype {
    public static User aUser(){
        User user = new User();
        user.setId(1);
        user.setLogin("alex123");
        user.setPassword("12345");
        user.setFirstname("Alex");
        user.setLastname("Bych");
        user.setPhoneNumber("88888888888");
        user.setRole(RolePrototype.aRole());
        user.setRating(0);
        user.setRatings(Set.of(RatingPrototype.aRating(), RatingPrototype.bRating()));
        return user;
    }

    public static UserDTO aUserDTO(){
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setLogin("alex123");
        user.setPassword("12345");
        user.setFirstname("Alex");
        user.setLastname("Bych");
        user.setPhoneNumber("88888888888");
        user.setRole(RolePrototype.aRole());
        user.setRating(0);
        user.setRatings(Set.of(RatingPrototype.aRating(), RatingPrototype.bRating()));
        return user;
    }
}
