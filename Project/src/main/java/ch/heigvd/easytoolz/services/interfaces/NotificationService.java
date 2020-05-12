package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.Notification;

public interface NotificationService {
    Notification storeNotification(Notification notification);
    Notification updateNotification(Notification notification, int id);
}
