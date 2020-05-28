package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.dto.LoanRequest;
import ch.heigvd.easytoolz.models.dto.PeriodRequest;
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

    /**
     * Update the state of a loan
     * @param loanId the id of the loan
     * @param state the state of the loan
     * @return ResponseEntity<String>
     */
    ResponseEntity<String> updateState(int loanId, State state);

    /**
     * Add a new period to the loan and a period request
     * @param loanId the id of the loan
     * @param periodRequest The period request (start and end date and the state of the period)
     * @return ResponseEntity<String>
     */
    ResponseEntity<String> addPeriod(int loanId, PeriodRequest periodRequest);

    /**
     * This method is used to update period state
     *
     * @param loanId the loan id to update period
     * @param periodId the period id to udpate
     * @param newState the new state
     * @return ResponseEntity<String>
     * @throws InvalidParameterException
     */
    ResponseEntity<String> updatePeriodState(int loanId, int periodId, State newState) throws InvalidParameterException;

    /**
     *  Search function used to get loans for a given user, in a given specific state in a given period
     *
     * @param username the username of the borrower or the owner
     * @param borrower a boolean to know if the user is borrower (true) or owner (false)
     * @param state state of the loan
     * @param city the city of the loan (borrower address)
     * @param dateStartLess The date used to select all the loans which begins after this date
     * @param dateEndLess The end date used to select all the loans which begins after this date
     * @param dateStartGreater The start date used to select all the loans which begins after this date
     * @param dateEndGreater The start date used to select all the loans which begins after this date
     * @return
     */
    List<Loan> getLoan(String username, boolean borrower, List<String> state, List<String> city, Date dateStartLess, Date dateEndLess,Date dateStartGreater, Date dateEndGreater);

    /**
     * Check if the current tool is currently borrowed
     * @param objectId
     * @return boolean
     */
    boolean isObjectIsCurrentlyBorrowed(int objectId);

    /**
     * Return the loans related to a user
     * @param username
     * @return a list of loans
     */
    List<Loan> getLoansRelatedTo(String username);

    /**
     * Send a email and a notification to the borrrower to warn him he has forgotten to give back the tool he borrows
     * @param loanId the id of the loan for which the tool hasn't been gived back
     * @return ResponseEntity<String>
     */
    ResponseEntity<String> askBack(int loanId);
}
