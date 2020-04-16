package ch.heigvd.easytoolz.exceptions.user;

public class UserFailedDeleteException extends RuntimeException{
    public UserFailedDeleteException(String name){
        super("Deletion of User " + name + "failed");
    }
}
