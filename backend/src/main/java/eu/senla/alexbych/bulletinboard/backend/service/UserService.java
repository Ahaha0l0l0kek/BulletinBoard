package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.ProfileEditRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.RegistrationRequest;
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
import lombok.AllArgsConstructor;
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

    private final IPostConverter postConverter;

    private final IUserConverter userConverter;

    private final RatingRepository ratingRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void editProfile(UserDTO user, ProfileEditRequest profile){
        if(!profile.getFirstname().isEmpty())
            user.setFirstname(profile.getFirstname());
        if(!profile.getLastname().isEmpty())
            user.setLastname(profile.getLastname());
        if(!profile.getPhoneNumber().isEmpty())
            user.setPhoneNumber(profile.getPhoneNumber());
        userRepository.save(userConverter.convertUserDTOToUser(user));
    }

    public boolean saveUser(RegistrationRequest request) {
        UserDTO user = new UserDTO();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLogin(request.getLogin());
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        userRepository.save(userConverter.convertUserDTOToUser(user));
        return true;
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
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user;
        }
        return null;
    }

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