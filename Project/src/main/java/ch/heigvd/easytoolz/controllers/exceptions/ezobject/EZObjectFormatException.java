package ch.heigvd.easytoolz.controllers.exceptions.ezobject;

public class EZObjectFormatException extends RuntimeException {
    EZObjectFormatException(String message)
    {
        super("Format Exception :"+ message);
    }
}
