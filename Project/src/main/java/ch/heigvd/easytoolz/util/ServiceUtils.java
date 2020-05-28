package ch.heigvd.easytoolz.util;

import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.StateNotification;
import ch.heigvd.easytoolz.models.User;

public class ServiceUtils {
    /**
     * transform any string in LIKE string for the query
     * for example :
     * s => 'henri'
     * return => '%henri%'
     * @param s a string
     * @return the string updated or null if s == null
     */
    public static String transformLike(String s) {
        if (s == null)
            return null;
        return "%" + s + "%";
    }

    /**
     * create a new notification
     * @param stateNotification State of the notification
     * @param recipient
     * @param args
     * @return
     */
    public static Notification createNotification(StateNotification stateNotification, User recipient, String ... args){
        return new Notification(String.format(stateNotification.getMessage(), args), stateNotification, recipient);
    }
}
