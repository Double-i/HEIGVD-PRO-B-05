package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.models.DTO.LoanRequest;
import ch.heigvd.easytoolz.models.DTO.PeriodRequest;
import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.State;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import java.security.InvalidParameterException;

public interface LoanService {

    /**
     * Store in db a loan. It checks the date, the availabilty of the tools and the other provided informations
     * @param newLoan Loan
     */
    ResponseEntity<String> store(LoanRequest newLoan);

    ResponseEntity<String> updateState(int loanId, State state);

    ResponseEntity<String> addPeriod(int loanId, PeriodRequest periodRequest);

    ResponseEntity<String> updatePeriodState(int loanId, int periodId, State newState) throws InvalidParameterException;

    List<Loan> getLoan(String username, boolean borrower, List<String> state, List<String> city, Date dateStart, Date dateEnd);
}
