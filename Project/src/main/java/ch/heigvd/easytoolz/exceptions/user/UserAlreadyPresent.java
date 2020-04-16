package ch.heigvd.easytoolz.exceptions.user;

public class UserAlreadyPresent extends RuntimeException {
    public UserAlreadyPresent(String name){
        super("User " + name + " is already present");
    }
}
