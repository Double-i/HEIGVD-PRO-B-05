package ch.heigvd.easytoolz.exceptions.notifications;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(int id){
        super("Notification " + id + "  not found");
    }
}
