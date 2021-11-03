package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.chat.dto.ChatUserDTO;
import eu.senla.alexbych.bulletinboard.chat.model.ChatUser;

public class ChatUserPrototype {
    public static ChatUser aChatUser(){
        ChatUser chatUser = new ChatUser();
        chatUser.setId(1);
        chatUser.setName("NotAlex");
        return chatUser;
    }

    public static ChatUser bChatUser(){
        ChatUser chatUser = new ChatUser();
        chatUser.setId(1);
        chatUser.setName("Alex");
        return chatUser;
    }

    public static ChatUserDTO aChatUserDTO(){
        ChatUserDTO chatUser = new ChatUserDTO();
        chatUser.setId(1);
        chatUser.setName("Alex");
        return chatUser;
    }
}
