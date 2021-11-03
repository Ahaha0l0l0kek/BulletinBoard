package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import eu.senla.alexbych.bulletinboard.chat.model.ChatUser;

public class ChatUserConverter {
    public ChatUser convertChatUserDTOToChatUser(ChatUserDTO chatUserDTO){
        ChatUser chatUser = new ChatUser();
        chatUser.setId(chatUserDTO.getId());
        chatUser.setName(chatUserDTO.getName());
        return chatUser;
    }
}
