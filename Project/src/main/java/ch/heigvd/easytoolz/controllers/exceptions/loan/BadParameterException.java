package ch.heigvd.easytoolz.controllers.exceptions.loan;

public class BadParameterException extends RuntimeException {
    public BadParameterException(String message)
    {
        super("Loan invalid "+ message);
    }
}
