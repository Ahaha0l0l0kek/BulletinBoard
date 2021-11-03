package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
