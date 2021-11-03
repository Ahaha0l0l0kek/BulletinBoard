package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import eu.senla.alexbych.bulletinboard.chat.model.ChatUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IChatUserConverter {
    ChatUser convertChatUserDTOToChatUser(ChatUserDTO chatUserDTO);
    ChatUserDTO convertChatUserToChatUserDTO(ChatUser chatUser);
}
