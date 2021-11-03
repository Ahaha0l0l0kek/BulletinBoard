package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserConverter {
    public UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setPosts(user.getPosts());
        userDTO.setRating(user.getRating());
        userDTO.setRatings(user.getRatings());
        return userDTO;
    }
}
