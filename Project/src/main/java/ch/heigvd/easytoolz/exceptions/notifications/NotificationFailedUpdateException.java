package ch.heigvd.easytoolz.exceptions.notifications;

public class NotificationFailedUpdateException extends RuntimeException {
    public NotificationFailedUpdateException(int id){
        super("Notification " + id + " failed to update");
    }
}
