package ch.heigvd.easytoolz.exceptions.loan;

public class LoanStateCantBeUpdatedException extends RuntimeException {
    public LoanStateCantBeUpdatedException(String message)
    {
        super("Only pending loans can have their state changed"+ message);
    }
}
