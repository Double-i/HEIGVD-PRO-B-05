package ch.heigvd.easytoolz.exceptions.loan;

public class LoanPeriodAlreadyPassedException extends RuntimeException{
public LoanPeriodAlreadyPassedException(String message)
    {
        super("Only pending loans can have their state changed"+ message);
    }
}
