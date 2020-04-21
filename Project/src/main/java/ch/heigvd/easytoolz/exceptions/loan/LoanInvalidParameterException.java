package ch.heigvd.easytoolz.exceptions.loan;

public class LoanInvalidParameterException extends RuntimeException {
    public LoanInvalidParameterException(String message)
    {
        super("Loan invalid "+ message);
    }
}
