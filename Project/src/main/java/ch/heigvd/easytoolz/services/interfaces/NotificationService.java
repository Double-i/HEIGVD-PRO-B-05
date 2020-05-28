package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.Notification;

public interface NotificationService {
    /**
     * stores the notification in the database
     * @param notification the new notification
     * @return the notification stored
     */
    Notification storeNotification(Notification notification);

    /**
     * updates the notification in the database
     * @param notification the notification updated
     * @param id id of the old notification
     * @return the notification updated
     */
    Notification updateNotification(Notification notification, int id);
}
