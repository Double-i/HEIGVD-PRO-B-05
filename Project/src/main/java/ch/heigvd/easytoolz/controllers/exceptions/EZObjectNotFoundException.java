package ch.heigvd.easytoolz.controllers.exceptions;

public class EZObjectNotFoundException extends RuntimeException {
    public EZObjectNotFoundException(String message)
    {
        super("Object not found :"+ message);
    }
}
