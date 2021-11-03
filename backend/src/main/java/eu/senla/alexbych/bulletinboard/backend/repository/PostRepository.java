package eu.senla.alexbych.bulletinboard.backend.repository;

import eu.senla.alexbych.bulletinboard.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p.* from posts p, categories c, users u where c.name = '?1' order by p.priority, u.rating desc",
    nativeQuery = true)
    List<Post> findPostsByCategory(String nameOfCategory);

    @Query(value = "select p.* from posts p, categories c, users u where c.name = '?1' order by p.priority, u.rating desc, p.?2",
            nativeQuery = true)
    List<Post> findPostsByCategoryWithOrder(String nameOfCategory, String nameOfOrder);

    @Query(value = "select p.* from posts p, users u order by p.priority, u.rating desc, p.?1",
            nativeQuery = true)
    List<Post> getAllPostsWithOrder(String nameOfOrder);

    @Query(value = "select p.* from posts p, users u where p.title % '?1' order by p.priority, u.rating desc, similarity(p.title, '?1')",
            nativeQuery = true)
    List<Post> searchPosts(String search);

    @Query(value = "select p.* from posts p, users u where p.title % '?1' order by p.priority, u.rating desc, similarity(p.title, '?1'), p.?2",
            nativeQuery = true)
    List<Post> searchPostsWithOrder(String search, String nameOfOrder);

    @Override
    @Query(value = "select NEW eu.senla.alexbych.bulletinboard.backend.dto.CustomerPost(p.id, p.title, p.price, p.picture) from Post p, User u order by p.priority, u.rating desc")
    List<Post> findAll();
}
