package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateCommentRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.repository.CommentRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.PostRepository;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.*;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.ChatUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final IPostConverter postConverter;
    private final ChatUserRepository chatUserRepository;
    private final ChatRepository chatRepository;
    private final IChatConverter chatConverter;
    private final IChatUserConverter chatUserConverter;
    private final CommentRepository commentRepository;
    private final ICommentConverter commentConverter;

    public boolean editPost(UserDTO user, long id, PostEditRequest request){
        PostDTO post = postConverter.convertPostToPostDTO(postRepository.getById(id));
        if(post.getUser().getId() == user.getId()) {
            if(!request.getTitle().isEmpty()) post.setTitle(request.getTitle());
            if(!request.getPicture().isEmpty()) post.setPicture(request.getPicture());
            if(!request.getDescription().isEmpty()) post.setDescription(request.getDescription());
            if(request.getPrice() != post.getPrice()) post.setPrice(request.getPrice());
            return true;
        }
            else return false;
    }

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
        List<PostDTO> posts = new ArrayList<>();
        for (Post post : postRepository
                .findAll()) {
            PostDTO postDTO = postConverter.convertPostToPostDTO(post);
            posts.add(postDTO);
        }
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

    public PostDTO createPost(CreatePostRequest request){
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(request.getTitle());
        postDTO.setPrice(request.getPrice());
        postDTO.setPicture(request.getPicture());
        postDTO.setDescription(request.getDescription());
        postDTO.setPostTime(LocalDateTime.now());
        postDTO.setPriority(request.isPriority());
        postDTO.setActive(true);
        postDTO.setCategory(request.getCategory());
        postDTO.setUser((User) SecurityContextHolder.getContext().getAuthentication());
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

    public CommentDTO createComment(UserDTO user, long id, CreateCommentRequest request){
        CommentDTO comment = new CommentDTO();
        comment.setUserId(user.getId());
        comment.setPostId(id);
        comment.setCommentTime(LocalDateTime.now());
        comment.setComment(request.getComment());
        commentRepository.save(commentConverter.convertCommentDTOToComment(comment));
        return comment;
    }
}
