package ch.heigvd.easytoolz.controllers.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name){
        super("User " + name + " not found");
    }
}
