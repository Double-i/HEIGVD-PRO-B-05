package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.ChatMessage;
import ch.heigvd.easytoolz.models.OutputMessage;
import ch.heigvd.easytoolz.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ChatController {

    @Autowired
    ChatRepository repository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/EZChat/{loan-conversation}/private")
    @SendTo("/secured/user/queue/loan-room/{loan-conversation}")
    public ChatMessage sendMessage(@Payload ChatMessage message,SimpMessageHeaderAccessor sha , @DestinationVariable("loan-conversation") String user)
    {

        repository.save(message);
        System.out.println("FROM ; "+sha.getUser().getName()+" TO "+ user + " SESSION " + sha.getSessionId());

        return message;

    }


}
