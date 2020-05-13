package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.ChatMessage;
import ch.heigvd.easytoolz.models.OutputMessage;
import ch.heigvd.easytoolz.models.StateNotification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.ChatRepository;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.util.ServiceUtils;
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

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @MessageMapping("/EZChat/{loan-conversation}/private")
    @SendTo("/secured/user/queue/loan-room/{loan-conversation}")
    public ChatMessage sendMessage(@Payload ChatMessage message,SimpMessageHeaderAccessor sha , @DestinationVariable("loan-conversation") String user)
    {
        repository.save(message);

        User recipient = userService.getUser(user);

        notificationService.storeNotification(ServiceUtils.createNotification(StateNotification.MESSAGE, recipient, message.getSender()));

        return message;

    }


}
