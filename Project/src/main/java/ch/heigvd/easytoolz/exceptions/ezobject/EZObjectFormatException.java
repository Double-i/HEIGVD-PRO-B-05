package ch.heigvd.easytoolz.exceptions.ezobject;

public class EZObjectFormatException extends RuntimeException {
    EZObjectFormatException(String message)
    {
        super("Format Exception :"+ message);
    }
}
