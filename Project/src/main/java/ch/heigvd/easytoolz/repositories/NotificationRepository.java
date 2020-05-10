package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    List<Notification> findByRecipient_UserName(String username);
}
