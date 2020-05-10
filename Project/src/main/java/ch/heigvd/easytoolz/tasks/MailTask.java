package ch.heigvd.easytoolz.tasks;

import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.NotificationRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.MailService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailTask {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MailService mailService;
    // Every minute, each notification non-read is sent
    @Scheduled(cron = "0 0 1 * * ?")
    public void execute(){
        List<User> users = userRepository.findAll();
        for(User user : users){
            for(Notification notification : notificationRepository.findByRecipient_UserName(user.getUserName())){
                if(!notification.isRead())
                    mailService.sendSimpleMessage(notification.getRecipient().getEmail(), notification.getState().getName(), notification.getMessage());
            }
        }
    }
}
