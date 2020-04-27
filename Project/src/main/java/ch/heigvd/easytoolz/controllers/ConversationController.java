package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.Conversation;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.ConversationRepository;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    ConversationRepository repository;

    @Autowired
    UserService service;

    @PostMapping("/create")
    public ResponseEntity<String> createConversation ( @RequestParam(name="sender") String sender,@RequestParam(name="recipient") String recipient )
    {

        User send = service.getUser(sender);
        User recp = service.getUser(recipient);
        repository.save(new Conversation(send,recp));
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
