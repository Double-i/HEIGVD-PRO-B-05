package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.ChatMessage;
import ch.heigvd.easytoolz.models.StateNotification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.ChatRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ChatController {

    @Autowired
    ChatRepository repository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    /**
     * redirige les message envyé au canal /EZChat/loan/private
     * vers les utilisateurs abonnés á /secured/user/queue/loan-room/loan
     * @param message
     * @param sha
     * @param user
     * @return
     */
    @MessageMapping("/EZChat/{loan-conversation}/private")
    @SendTo("/secured/user/queue/loan-room/{loan-conversation}")
    public ChatMessage sendMessage(@Payload ChatMessage message,SimpMessageHeaderAccessor sha , @DestinationVariable("loan-conversation") String user)
    {
        Optional<User> optionalRecipient = userRepository.findById(message.getRecipient());

        if(optionalRecipient.isEmpty())
            throw new UserNotFoundException(message.getRecipient());

        User recipient = optionalRecipient.get();

        repository.save(message);
        notificationService.storeNotification(ServiceUtils.createNotification(StateNotification.MESSAGE, recipient, message.getSender()));

        return message;

    }


}
