package ch.heigvd.easytoolz.exceptions.ezobject;

public class UserHasNoObjectException extends RuntimeException{
    public UserHasNoObjectException(String message)
    {
        super("No objects found for user : "+ message);
    }

}
