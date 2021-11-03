package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.repository.RatingRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.RoleRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.UserRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IPostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IUserConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.PostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.UserConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPost;
import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPostDTO;
import static eu.senla.alexbych.bulletinboard.backend.prototype.RolePrototype.aRole;
import static eu.senla.alexbych.bulletinboard.backend.prototype.UserPrototype.aUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostConverter postConverter;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() {
        User user = aUser();
        when(roleRepository.findByName(anyString())).thenReturn(aRole());
        when(passwordEncoder.encode(anyString())).thenReturn("encode password");
        when(userRepository.save(user)).thenReturn(user);
        User actual = userService.saveUser(user);
        assertThat(actual).isEqualToIgnoringGivenFields(user, "password");

    }

    @Test
    public void getPostsByUserId(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(userRepository.getById(anyLong()).getPosts()).thenReturn(Set.of(post));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = userService.getPostsByUserId(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findByLogin() {
        User expected = aUser();
        when(userRepository.findByLogin(anyString())).thenReturn(aUser());
        User actual = userService.findByLogin("test");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findByLoginAndPassword() {
        User user = aUser();
        User actual = userService.findByLogin("test");
    }

    @Test
    public void update() {

    }

    //something hard
    @Test
    public void loadUserByUsername() {

    }

    @Test
    public void setRating(){

    }
}
