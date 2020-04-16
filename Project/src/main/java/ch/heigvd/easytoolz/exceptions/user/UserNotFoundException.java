package ch.heigvd.easytoolz.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name){
        super("User " + name + " not found");
    }
}
