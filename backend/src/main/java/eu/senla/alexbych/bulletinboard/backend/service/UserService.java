package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.ProfileEditRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.RegistrationRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Rating;
import eu.senla.alexbych.bulletinboard.backend.model.Role;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.repository.PostRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.RatingRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.RoleRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.UserRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.compatator.PostComparator;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IPostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IUserConverter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private  final PostRepository postRepository;

    private final IPostConverter postConverter;

    private final IUserConverter userConverter;

    private final RatingRepository ratingRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO editProfile(UserDTO user, ProfileEditRequest profile){
        if(!profile.getFirstname().isEmpty())
            user.setFirstname(profile.getFirstname());
        if(!profile.getLastname().isEmpty())
            user.setLastname(profile.getLastname());
        if(!profile.getPhoneNumber().isEmpty())
            user.setPhoneNumber(profile.getPhoneNumber());
        userRepository.save(userConverter.convertUserDTOToUser(user));
        return user;
    }

    @Transactional
    public UserDTO saveUser(RegistrationRequest request) {
        UserDTO user = new UserDTO();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRating(0);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhoneNumber(request.getPhoneNumber());
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        userRepository.save(userConverter.convertUserDTOToUser(user));
        return user;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostsByUserId(long id){
        return postRepository.getPostByUserId(id)
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .sorted(Comparator.comparing(PostDTO::isPriority))
                .sorted(new PostComparator())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findByLogin(String login) {
        return userConverter.convertUserToUserDTO(userRepository.findByLogin(login));
    }

    @Transactional(readOnly = true)
    public UserDTO findByLoginAndPassword(String login, String password) {
        UserDTO user = findByLogin(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user;
        }
        return null;
    }

    @Transactional
    public boolean update(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            Role role = new Role();
            role.setId(1);
            user.setRole(role);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @Override
    @Transactional
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = findByLogin(username);
        user.getAuthorities();
        return user;
    }

    @Transactional
    public void setRating(long id, int rating){
        Rating ratingObject = new Rating();
        ratingObject.setUserId(id);
        ratingObject.setRate(rating);
        ratingRepository.save(ratingObject);
        List<Rating> ratings = ratingRepository.findRatingsByUserId(id);
        float sum = 0;
        if(ratings.isEmpty()){
            sum = rating;
        } else {
            for (Rating ratingA : ratings) {
                sum += ratingA.getRate();
            }
            sum = sum/ratings.size();
        }
        userRepository.updateRating(sum, id);
    }
}