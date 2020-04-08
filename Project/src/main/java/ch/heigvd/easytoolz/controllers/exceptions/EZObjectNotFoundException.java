package ch.heigvd.easytoolz.controllers.exceptions;

public class EZObjectNotFoundException extends RuntimeException {
    public EZObjectNotFoundException()
    {
        super("Object not found");
    }
}
