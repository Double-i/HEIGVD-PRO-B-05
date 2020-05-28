package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    /**
     * @param username the username of the recipient
     * @return sort the notifications by the username of the recipient
     */
    List<Notification> findByRecipient_UserName(String username);
}
