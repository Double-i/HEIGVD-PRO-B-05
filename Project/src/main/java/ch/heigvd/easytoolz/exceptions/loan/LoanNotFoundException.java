package ch.heigvd.easytoolz.exceptions.loan;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message)
    {
        super("Loan not found "+ message);
    }
}
