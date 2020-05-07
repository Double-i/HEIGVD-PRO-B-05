package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.models.json.SuccessResponse;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/{id}/markRead")
    public Notification markRead(@PathVariable int id){
        Notification newNotif = new Notification();
        newNotif.setIsRead(true);
        return notificationService.updateNotification(newNotif, id);
    }

    @PostMapping("/{id}/markUnread")
    public Notification markUnread(@PathVariable int id){
        Notification newNotif = new Notification();
        newNotif.setIsRead(false);
        return notificationService.updateNotification(newNotif, id);
    }
}
