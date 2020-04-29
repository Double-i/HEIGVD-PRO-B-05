package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.ChatMessage;
import ch.heigvd.easytoolz.models.OutputMessage;
import ch.heigvd.easytoolz.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
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

    @MessageMapping("/secured/user/queue/specific-user/{conv}")
    public ChatMessage sendMessage(@Payload ChatMessage message, Principal user, @DestinationVariable("conv") int conv)
    {

        OutputMessage out = new OutputMessage(
                message.getSender(),
                message.getRecipient(),
                new SimpleDateFormat("HH:mm").format(new Date())
        );

        repository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient(),"/specific-user/"+conv,out);

        return message;

    }


}
