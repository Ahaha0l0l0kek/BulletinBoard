package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.service.ChatService;
import eu.senla.alexbych.bulletinboard.chat.dto.ChatDTO;
import eu.senla.alexbych.bulletinboard.chat.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> createChatWithSeller(@PathVariable long id){
        ChatDTO chat = chatService.getById(id);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<Object> getMessagesOfChat(@PathVariable long id){
        return new ResponseEntity<>(chatService.getMessages(id), HttpStatus.OK);
    }
}
