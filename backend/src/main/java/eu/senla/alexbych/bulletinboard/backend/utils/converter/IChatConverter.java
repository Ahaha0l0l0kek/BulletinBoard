package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.model.Chat;
import org.mapstruct.Mapper;

@Mapper
public interface IChatConverter {
    Chat convertChatDTOToChat(ChatDTO chatDTO);
    ChatDTO convertChatToChatDTO(Chat chat);
}
