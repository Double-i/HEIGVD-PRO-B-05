package ch.heigvd.easytoolz.exceptions.loan;

public class LoanInvalidUserException extends RuntimeException {
    public LoanInvalidUserException(String message)
    {
        super("Loan: invalid user "+ message);
    }
}