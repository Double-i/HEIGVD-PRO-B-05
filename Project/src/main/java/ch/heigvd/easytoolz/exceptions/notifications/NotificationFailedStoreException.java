package ch.heigvd.easytoolz.exceptions.notifications;

public class NotificationFailedStoreException extends RuntimeException {
    public NotificationFailedStoreException(int id){
        super("Notification " + id + " failed to store");
    }
}
