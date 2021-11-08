package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.repository.ChatRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.MessageRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IChatConverter;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.model.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final IChatConverter chatConverter;

    private final MessageRepository messageRepository;


    public ChatDTO getById(long id) {
        return chatConverter.convertChatToChatDTO(chatRepository.getById(id));
    }

    public List<Message> getMessages(long id) {
        return messageRepository.findAll(id);
    }
}
