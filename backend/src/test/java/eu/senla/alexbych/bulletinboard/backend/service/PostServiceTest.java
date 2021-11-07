package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateCommentRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.repository.*;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.*;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static eu.senla.alexbych.bulletinboard.backend.prototype.CommentPrototype.aComment;
import static eu.senla.alexbych.bulletinboard.backend.prototype.CommentPrototype.aCommentDTO;
import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPost;
import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPostDTO;
import static eu.senla.alexbych.bulletinboard.backend.prototype.UserPrototype.aUser;
import static eu.senla.alexbych.bulletinboard.backend.prototype.UserPrototype.aUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private IPostConverter postConverter;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ICommentConverter commentConverter;

    @MockBean
    private IUserConverter userConverter;

    @Autowired
    private PostService postService;


    @Test
    void getPostById(){
        Post post = aPost();
        PostDTO expected = aPostDTO();
        when(postRepository.getById(anyLong())).thenReturn(post);
        when(postConverter.convertPostToPostDTO(post)).thenReturn(expected);
        PostDTO actual = postService.getPostById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void boostPostPriority(){
        Post post = aPost();
        PostDTO expected = aPostDTO();
        expected.setPriority(true);
        when(postRepository.getById(anyLong())).thenReturn(post);
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        when(postConverter.convertPostDTOToPost(aPostDTO())).thenReturn(aPost());
        when(postRepository.save(post)).thenReturn(post);
        PostDTO actual = postService.boostPostPriority(1);
        assertThat(actual).usingRecursiveComparison().ignoringFields("user").isEqualTo(expected);
    }

    @Test
    void getAllPosts(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.findAll()).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.getAllPosts();
        assertThat(actual).usingRecursiveComparison().ignoringFields("user").isEqualTo(expected);
    }

    @Test
    void deletePost(){
        postService.deletePost(aPost().getId());
        verify(postRepository, times(1)).deleteById(aPost().getId());
    }

    @Test
    void createPost(){
        CreatePostRequest createPostRequest =
                new CreatePostRequest("Sofa", "sofa.png", "nice sofa", false, 1);
        createPostRequest.setPrice(2300);
        Post post = aPost();
        UserDTO user = aUserDTO();
        PostDTO expected = aPostDTO();
        when(postRepository.save(any())).thenReturn(aPost());
        when(postConverter.convertPostToPostDTO(post)).thenReturn(expected);
        when(userConverter.convertUserDTOToUser(aUserDTO())).thenReturn(aUser());
        PostDTO actual = postService.createPost(createPostRequest, user);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "postTime", "user").isEqualTo(expected);
    }

    @Test
    void searchPosts(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.searchPosts(anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.searchPosts("some peace of some title", 0, 0);
        assertThat(actual).usingRecursiveComparison().ignoringFields("user").isEqualTo(expected);
    }

    @Test
    void searchPostsWithOrder(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.searchPostsWithOrder(anyString(), anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.searchPostsWithOrder("some peace of some title", "some order", 0, 0);
        assertThat(actual).usingRecursiveComparison().ignoringFields("user").isEqualTo(expected);
    }

    @Test
    void createComment() {
        CommentDTO expected = aCommentDTO();
        CreateCommentRequest request = new CreateCommentRequest("super comment");
        when(commentRepository.save(any())).thenReturn(aComment());
        when(commentConverter.convertCommentDTOToComment(any())).thenReturn(aComment());
        CommentDTO actual = postService.createComment(aUserDTO(), 1, request);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "commentTime").isEqualTo(expected);
    }
}
