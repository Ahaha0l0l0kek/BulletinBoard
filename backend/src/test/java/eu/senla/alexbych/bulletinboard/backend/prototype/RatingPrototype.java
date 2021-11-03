package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.model.Rating;

public class RatingPrototype {
    public static Rating aRating(){
        Rating rating = new Rating();
        rating.setId(1);
        rating.setUserId(1);
        rating.setRate(3);
        return rating;
    }

    public static Rating bRating(){
        Rating rating = new Rating();
        rating.setId(2);
        rating.setUserId(1);
        rating.setRate(5);
        return rating;
    }
}
