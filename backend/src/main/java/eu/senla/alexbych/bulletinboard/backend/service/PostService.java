package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.CreateCommentRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.CreatePostRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.PostEditRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.CommentDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Post;
import eu.senla.alexbych.bulletinboard.backend.repository.*;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.utils.compatator.PostComparator;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.*;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
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
    private final CategoryRepository categoryRepository;
    private final IUserConverter userConverter;

    @Transactional
    public PostDTO editPost(UserDTO user, long id, PostEditRequest request){
        Post post = postRepository.getById(id);
        if(post.getUser().getId() == user.getId()) {
            if(!request.getTitle().isEmpty()) post.setTitle(request.getTitle());
            if(!request.getPicture().isEmpty()) post.setPicture(request.getPicture());
            if(!request.getDescription().isEmpty()) post.setDescription(request.getDescription());
            if(request.getPrice() != post.getPrice()) post.setPrice(request.getPrice());
            postRepository.save(post);
            return postConverter.convertPostToPostDTO(post);
        }
            else return null;
    }

    @Transactional(readOnly = true)
    public PostDTO getPostById(long id){
        return postConverter.convertPostToPostDTO(postRepository.getById(id));
    }

    public PostDTO boostPostPriority(long id){
        PostDTO post = postConverter.convertPostToPostDTO(postRepository.getById(id));
        post.setPriority(true);
        postRepository.save(postConverter.convertPostDTOToPost(post));
        return post;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostsByCategory(String nameOfCategory){
        return categoryRepository
                .findCategoryByCategoryName(nameOfCategory)
                .getPosts()
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .sorted(Comparator.comparing(PostDTO::isPriority))
                .sorted(new PostComparator())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts(){
        List<PostDTO> posts = new ArrayList<>();
        for (Post post : postRepository
                .findAll()) {
            PostDTO postDTO = postConverter.convertPostToPostDTO(post);
            posts.add(postDTO);
        }
        posts.sort(Comparator.comparing(PostDTO::isPriority));
        posts.sort(new PostComparator());
        return posts;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getAllPostsWithOrder(String nameOfOrder){
        return postRepository.findAll(Sort.by(Sort.Direction.ASC, nameOfOrder))
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(long id){
        postRepository.deleteById(id);
    }

    @Transactional
    public PostDTO createPost(CreatePostRequest request, UserDTO user){
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(request.getTitle());
        postDTO.setPrice(request.getPrice());
        postDTO.setPicture(request.getPicture());
        postDTO.setDescription(request.getDescription());
        postDTO.setPostTime(LocalDateTime.now());
        postDTO.setPriority(request.isPriority());
        postDTO.setActive(true);
        postDTO.setCategoryId(request.getCategoryId());
        postDTO.setUser(userConverter.convertUserDTOToUser(user));
        postRepository.save(postConverter.convertPostDTOToPost(postDTO));
        return postDTO;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public List<PostDTO> searchPosts(String search, float minPrice, float maxPrice){
        List<PostDTO> list = postRepository.searchPosts(search).stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
        if(minPrice != 0) return list.stream().filter(o -> o.getPrice() > minPrice).collect(Collectors.toList());
        if(maxPrice != 0) return list.stream().filter(o -> o.getPrice() < maxPrice).collect(Collectors.toList());
        return list;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> searchPostsWithOrder(String search, String order, float minPrice, float maxPrice){
        List<PostDTO> list = postRepository.searchPostsWithOrder(search, order).stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
        if(minPrice != 0) return list.stream().filter(o -> o.getPrice() > minPrice).collect(Collectors.toList());
        if(maxPrice != 0) return list.stream().filter(o -> o.getPrice() < maxPrice).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public CommentDTO createComment(UserDTO user, long id, CreateCommentRequest request){
        CommentDTO comment = new CommentDTO();
        comment.setUserId(user.getId());
        comment.setPostId(id);
        comment.setCommentTime(LocalDateTime.now());
        comment.setCommentText(request.getComment());
        commentRepository.save(commentConverter.convertCommentDTOToComment(comment));
        return comment;
    }
}
