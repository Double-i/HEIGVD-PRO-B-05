package ch.heigvd.easytoolz.controllers.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name){
        super("User " + name + " not found");
    }
}
