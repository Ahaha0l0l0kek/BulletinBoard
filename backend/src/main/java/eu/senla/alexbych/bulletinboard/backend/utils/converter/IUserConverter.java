package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserConverter {
    User convertUserDTOToUser(UserDTO userDTO);
    UserDTO convertUserToUserDTO(User user);
}
