package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.repository.PostRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.*;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static eu.senla.alexbych.bulletinboard.backend.prototype.ChatPrototype.aChat;
import static eu.senla.alexbych.bulletinboard.backend.prototype.ChatPrototype.aChatDTO;
import static eu.senla.alexbych.bulletinboard.backend.prototype.ChatUserPrototype.*;
import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPost;
import static eu.senla.alexbych.bulletinboard.backend.prototype.PostPrototype.aPostDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private IPostConverter postConverter;

    @MockBean
    private ChatUserRepository chatUserRepository;

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private IChatConverter chatConverter;

    @MockBean
    private IChatUserConverter chatUserConverter;

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
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void getPostsByCategory(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.findPostsByCategory(anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.getPostsByCategory("some category");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void getAllPosts(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.findAll()).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.getAllPosts();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void findPostsByCategoryWithOrder(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.findPostsByCategoryWithOrder(anyString(), anyString()))
                .thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService
                .findPostsByCategoryWithOrder("some category", "some order");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void getAllPostsWithOrder(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.getAllPostsWithOrder(anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.getAllPostsWithOrder("some order");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void deletePost(){
        postService.deletePost(aPost().getId());
        verify(postRepository, times(1)).deleteById(aPost().getId());
    }

    @Test
     void createPost(){
        Post post = aPost();
        PostDTO expected = aPostDTO();
        when(postRepository.save(any())).thenReturn(post);
        when(postConverter.convertPostToPostDTO(post)).thenReturn(expected);
 //       PostDTO actual = postService.createPost(aPostDTO());
  //      assertThat(actual).isEqualTo(expected);
    }

    @Test
     void createChatWithSeller(){
        ChatDTO expected = aChatDTO();
        when(postRepository.getById(anyLong())).thenReturn(aPost());
        when(chatUserRepository.save(aChatUser())).thenReturn(aChatUser());
        when(chatUserConverter.convertChatUserDTOToChatUser(aChatUserDTO()))
                .thenReturn(bChatUser())
                .thenReturn(aChatUser())
                .thenReturn(bChatUser())
                .thenReturn(aChatUser());
        when(chatRepository.save(aChat())).thenReturn(aChat());
        when(chatConverter.convertChatDTOToChat(aChatDTO())).thenReturn(aChat());
        ChatDTO actual = postService.createChatWithSeller(1, "Alex");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void searchPosts(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.searchPosts(anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.searchPosts("some peace of some title");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
     void searchPostsWithOrder(){
        Post post = aPost();
        List<PostDTO> expected = new ArrayList<>();
        expected.add(aPostDTO());
        when(postRepository.searchPostsWithOrder(anyString(), anyString())).thenReturn(new ArrayList<>(List.of(post)));
        when(postConverter.convertPostToPostDTO(post)).thenReturn(aPostDTO());
        List<PostDTO> actual = postService.searchPostsWithOrder("some peace of some title", "some order");
        assertThat(actual).isEqualTo(expected);
    }
}
