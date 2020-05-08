package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedNotAdminException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationFailedStoreException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationNotFoundException;
import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.NotificationRepository;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    public final ApplicationEventPublisher eventPublisher;

    public NotificationServiceImpl(ApplicationEventPublisher eventPublisher, NotificationRepository notificationRepository, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public Notification storeNotification(Notification notification) {
        Notification newNotification = notificationRepository.save(notification);
        if(notificationRepository.findById(newNotification.getID()).isEmpty()){
            throw new NotificationFailedStoreException(newNotification.getID());
        }
        // Publish notification for "realtime" notification SSE
        this.eventPublisher.publishEvent(newNotification);

        return newNotification;
    }

    @Override
    public Notification updateNotification(Notification newNotification, int id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if(optionalNotification.isEmpty()){
            throw new NotificationNotFoundException(id);
        }else{
            Notification oldNotification = optionalNotification.get();

            if(!authenticationService.isTheCurrentUserAdmin()){
                if(!authenticationService.getTheDetailsOfCurrentUser().getUserName().equals(oldNotification.getRecipient().getUserName())){
                    throw new AccessDeniedNotAdminException();
                }
            }

            if(newNotification.getMessage() != null) oldNotification.setMessage(newNotification.getMessage());
            if(newNotification.getRecipient() != null){
                User newUser = userService.getUser(newNotification.getRecipient().getUserName());
                oldNotification.setRecipient(newUser);
            }
            if(newNotification.getState() != null){
                oldNotification.setState(newNotification.getState());
            }
            if(newNotification.isIsRead() != oldNotification.isIsRead())
                oldNotification.setIsRead(newNotification.isIsRead());
            return notificationRepository.save(oldNotification);
        }
    }
}
