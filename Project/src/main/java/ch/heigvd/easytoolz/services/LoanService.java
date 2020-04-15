package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.State;
import org.springframework.http.ResponseEntity;

public interface LoanService {

    /**
     * Store in db a loan. It checks the date, the availabilty of the tools and the other provided informations
     * @param newLoan Loan
     */
    ResponseEntity<String> store(Loan newLoan);

    ResponseEntity<String> updateState(int loanId, State state);
}
