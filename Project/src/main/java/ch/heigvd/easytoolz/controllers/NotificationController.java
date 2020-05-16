package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedException;
import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    // Use ConcurrentHashMap (thread-safe hashmap) to store the username with the corresponding emitter
    // to be able to notify only the right user
    private final ConcurrentHashMap<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    @Autowired
    public NotificationService notificationService;
    @Autowired
    public AuthenticationService authService;

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


    /**
     * This method allows the user to subscribe to the notifications. Every user has a collection of emitters.
     * Every emitters will send the notification to instance of the frontend application. We use a collection of emitters
     * to allow the user to be connected to several web tabs/web browsers (1 emitter = 1 browsers/tab).
     *
     * @param username the string of the user's username
     * @return SseEmitter the emitter which will be used by the tabs/web browser
     */
    @GetMapping("/{username}")
    public SseEmitter subscribeToNotifications(@PathVariable String username) {

        User user = authService.getTheDetailsOfCurrentUser();
        System.out.println("Logged in user : " + user);
        if (user == null || !user.getUserName().equals(username))
            throw new AccessDeniedException();

        // Create a new emitter for the user
        SseEmitter emitter = new SseEmitter();

        // Get the user list of emitters - the user might have several emitter if he has opened several tabs in his
        // web browser
        List<SseEmitter> userEmitters = emitters.get(username);
        // If the user hasn't yet a list of emitters we create it
        if(userEmitters == null){
            userEmitters = new ArrayList<>();
            this.emitters.put(username, userEmitters);
        }
        // We add the emitter to his list
        userEmitters.add(emitter);

        System.out.println("Subscribe user : " + username);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.get(username).remove(emitter);
        });

        return emitter;

    }
    /**
     * This method is used to send the notification to all the users who have been registered by subscribeToNotifications
     *
     * @param notification The notification to send to the user.
     */

    @EventListener
    public void onNotification(Notification notification) {


        String username = notification.getRecipient().getUserName();

        // Get all the emitter of the user
        List<SseEmitter> userEmitters = this.emitters.get(username);

        // If the user has an entry in the emitters.
        if(userEmitters != null){

            ArrayList<SseEmitter> deadEmitters = new ArrayList<>();
            // Send notification to all the emitters
            for (SseEmitter userEmitter : userEmitters) {
                try {
                    userEmitter.send(notification);
                    //userEmitters.get(i).send(notification);
                    System.out.println("has been sent to user : " + username);

                } catch (Exception e) {
                    // If the emitters seems dead, add it to the collection of emitters which will be deleted
                    deadEmitters.add(userEmitter);
                }
            }

            // Delete all the dead emitters and remove the entry in the hashtable if the user hasn't any emitters.
            userEmitters.remove(deadEmitters);
            if(this.emitters.get(username).size() == 0){
                this.emitters.remove(username);
            }

        }
    }
}
