package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from posts where title % :search order by priority, similarity(title, :search)",
            nativeQuery = true)
    List<Post> searchPosts(String search);

    @Query(value = "select * from posts where title % :search" +
            " order by priority, similarity(title, :search), :nameOfOrder",
            nativeQuery = true)
    List<Post> searchPostsWithOrder(String search, String nameOfOrder);

    @Query(value = "select * from posts where seller_id = ?1",
    nativeQuery = true)
    List<Post> getPostByUserId(long userId);
}
