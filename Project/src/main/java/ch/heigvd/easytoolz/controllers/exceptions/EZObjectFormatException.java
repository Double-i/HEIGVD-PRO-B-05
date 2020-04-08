package ch.heigvd.easytoolz.controllers.exceptions;

public class EZObjectFormatException extends RuntimeException {
    EZObjectFormatException(String message)
    {
        super("Format Exception :"+ message);
    }
}
