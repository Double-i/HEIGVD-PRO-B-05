package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.State;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LoanService {

    /**
     * Store in db a loan. It checks the date, the availabilty of the tools and the other provided informations
     * @param newLoan Loan
     */
    ResponseEntity<String> store(Loan newLoan);

    ResponseEntity<String> updateState(int loanId, State state);

    List<Loan> getLoan(String username, boolean borrower, String state);

}
