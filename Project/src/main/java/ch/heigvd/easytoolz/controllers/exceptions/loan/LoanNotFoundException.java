package ch.heigvd.easytoolz.controllers.exceptions.loan;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message)
    {
        super("Loan not found "+ message);
    }
}
