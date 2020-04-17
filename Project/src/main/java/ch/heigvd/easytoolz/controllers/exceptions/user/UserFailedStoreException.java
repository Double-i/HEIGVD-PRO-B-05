package ch.heigvd.easytoolz.controllers.exceptions.user;

public class UserFailedStoreException extends RuntimeException {
    public UserFailedStoreException(String name){
        super("Fail to store the user " + name);
    }
}
