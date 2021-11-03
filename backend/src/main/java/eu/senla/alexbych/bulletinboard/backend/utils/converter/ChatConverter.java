package eu.senla.alexbych.bulletinboard.backend.utils.converter;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.model.Chat;

public class ChatConverter {
    public Chat convertChatDTOToChat(ChatDTO chatDTO){
        Chat chat = new Chat();
        chat.setId(chatDTO.getId());
        chat.setSeller(chatDTO.getSeller());
        chat.setBuyer(chatDTO.getBuyer());
        chat.setMessages(chatDTO.getMessages());
        return chat;
    }
}
