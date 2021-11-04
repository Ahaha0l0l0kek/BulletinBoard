package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.model.Chat;

public class ChatPrototype {
    public static Chat aChat(){
        Chat chat = new Chat();
        chat.setId(1);
        chat.setSeller(ChatUserPrototype.aChatUser());
        chat.setBuyer(ChatUserPrototype.bChatUser());
        chat.setMessages(null);
        return chat;
    }

    public static ChatDTO aChatDTO(){
        ChatDTO chat = new ChatDTO();
        chat.setId(1);
        chat.setSeller(ChatUserPrototype.aChatUser());
        chat.setBuyer(ChatUserPrototype.bChatUser());
        chat.setMessages(null);
        return chat;
    }
}
