package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.Rating;
import eu.senla.alexbych.bulletinboard.backend.model.Role;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import eu.senla.alexbych.bulletinboard.backend.repository.RatingRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.RoleRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.UserRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IPostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IUserConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.PostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.UserConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PostConverter postConverter;

    private final UserConverter userConverter;

    private final RatingRepository ratingRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public List<PostDTO> getPostsByUserId(long id){
        return userRepository
                .getById(id)
                .getPosts()
                .stream()
                .map(postConverter::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User update(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            Role role = new Role();
            role.setId(1);
            user.setRole(role);
            userRepository.save(user);
            return user;
        } else return null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        user.getAuthorities();
        return user;
    }

    public void setRating(long id, int rating){
        UserDTO user = userConverter.convertUserToUserDTO(userRepository.getById(id));
        Rating ratingObject = new Rating();
        ratingObject.setUserId(id);
        ratingObject.setRate(rating);
        ratingRepository.save(ratingObject);
        Set<Rating> ratings = user.getRatings();
        float sum = 0;
        for (Rating ratingA : ratings) {
            sum += ratingA.getRate();
        }
        sum = sum/ratings.size();
        user.setRating(sum);
    }
}