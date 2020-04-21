package ch.heigvd.easytoolz.controllers.exceptions.loan;

public class LoanInvalidUserException extends RuntimeException {
    public LoanInvalidUserException(String message)
    {
        super("Loan: invalid user "+ message);
    }
}