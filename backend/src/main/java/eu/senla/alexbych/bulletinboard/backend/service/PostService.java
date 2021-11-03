package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.repository.PostRepository;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.ChatConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.ChatUserConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.PostConverter;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatUserRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IChatUserConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final ChatUserRepository chatUserRepository;
    private final ChatRepository chatRepository;
    private final ChatConverter chatConverter;
    private final ChatUserConverter chatUserConverter;

    public PostDTO getPostById(long id){
        return postConverter.convertPostToPostDTO(postRepository.getById(id));
    }

    public PostDTO boostPostPriority(long id){
        PostDTO post = postConverter.convertPostToPostDTO(postRepository.getById(id));
        post.setPriority(true);
        postRepository.save(postConverter.convertPostDTOToPost(post));
        return post;
    }

    public List<PostDTO> getPostsByCategory(String nameOfCategory){
        return postRepository
                .findPostsByCategory(nameOfCategory)
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllPosts(){
        List<PostDTO> posts = postRepository
                .findAll()
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
        posts.sort(Comparator.comparing(PostDTO::isPriority));
        return posts;
    }

    public List<PostDTO> findPostsByCategoryWithOrder(String nameOfCategory, String nameOfOrder){
        return postRepository
                .findPostsByCategoryWithOrder(nameOfCategory, nameOfOrder)
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllPostsWithOrder(String nameOfOrder){
        return postRepository.getAllPostsWithOrder(nameOfOrder)
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public void deletePost(long id){
        postRepository.deleteById(id);
    }

    public PostDTO createPost(PostDTO postDTO){
        postRepository.save(postConverter.convertPostDTOToPost(postDTO));
        return postDTO;
    }

    public ChatDTO createChatWithSeller(long id, String buyerName){
        ChatDTO chat = new ChatDTO();
        ChatUserDTO seller = new ChatUserDTO();
        ChatUserDTO buyer = new ChatUserDTO();

        seller.setName(postRepository.getById(id).getUser().getFirstname());
        chatUserRepository.save(chatUserConverter.convertChatUserDTOToChatUser(seller));
        buyer.setName(buyerName);
        chatUserRepository.save(chatUserConverter.convertChatUserDTOToChatUser(buyer));

        chat.setSeller(chatUserConverter.convertChatUserDTOToChatUser(seller));
        chat.setBuyer(chatUserConverter.convertChatUserDTOToChatUser(buyer));

        chatRepository.save(chatConverter.convertChatDTOToChat(chat));

        return chat;
    }

    public List<PostDTO> searchPosts(String search){
        return postRepository.searchPosts(search).stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> searchPostsWithOrder(String search, String order){
        return postRepository.searchPostsWithOrder(search, order).stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }
}
