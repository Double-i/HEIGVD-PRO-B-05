package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.notifications.NotificationFailedStoreException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationNotFoundException;
import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.NotificationRepository;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;

public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    @Override
    public Notification storeNotification(Notification notification) {
        Notification newNotification = notificationRepository.save(notification);
        if(notificationRepository.findById(newNotification.getID()).isEmpty()){
            throw new NotificationFailedStoreException(newNotification.getID());
        }
        return newNotification;
    }

    @Override
    public Notification updateNotification(Notification newNotification, int id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if(optionalNotification.isEmpty()){
            throw new NotificationNotFoundException(id);
        }else{
            Notification oldNotification = optionalNotification.get();
            if(newNotification.getMessage() != null) oldNotification.setMessage(newNotification.getMessage());
            if(newNotification.getRecipient() != null){
                User newUser = userService.getUser(newNotification.getRecipient().getUserName());
                oldNotification.setRecipient(newUser);
            }
            if(newNotification.getState() != null){
                oldNotification.setState(newNotification.getState());
            }
            if(newNotification.isRead() != oldNotification.isRead())
                oldNotification.setRead(newNotification.isRead());
            return notificationRepository.save(oldNotification);
        }
    }
}
