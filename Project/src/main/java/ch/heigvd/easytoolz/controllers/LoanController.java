package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.models.*;

import ch.heigvd.easytoolz.models.DTO.LoanRequest;
import ch.heigvd.easytoolz.models.DTO.PeriodRequest;
import ch.heigvd.easytoolz.models.DTO.StateRequest;
import ch.heigvd.easytoolz.repositories.LoanRepository;
import ch.heigvd.easytoolz.services.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    LoanService loanService;


    /**
     * Get the list of all the loans
     *
     * @return
     */
    @GetMapping
    public List<Loan> index() {
        return loanRepository.findAll();
    }


    /**
     * Find loans by borrower or user
     * url: /api/loan/find/{username}
     *
     * @param username
     * @return
     */
    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<Loan> getLoanByUser(@PathVariable String username, @RequestParam boolean borrower,
                                    @RequestParam(required = false) String state)
    {

    return loanService.getLoan(username, borrower, state);

    }

    /**
     * Add a loan into the database
     * url: POST /api/loans
     *
     * @param newLoan
     * @return
     */
    @PostMapping
    public ResponseEntity<String> addLoan(@RequestBody LoanRequest newLoan) {
        System.out.println(newLoan);
        return loanService.store(newLoan);
    }

    /**
     * Cancel a loan that hasn't been accepted by the tool's owner
     *
     * @param loanId int
     * @return
     */
    @PatchMapping("/{loanId}/state")
    public ResponseEntity<String> updateState(@PathVariable int loanId, @RequestBody StateRequest stateRequest ) {

            State state = State.valueOf(stateRequest.getState());

            return loanService.updateState(loanId,state);
    }

    @PostMapping("/{loanId}/periods/")
    public ResponseEntity<String> addPeriod(@PathVariable int loanId, @RequestBody PeriodRequest periodRequest){
        return loanService.addPeriod( loanId,  periodRequest);
    }


    @PatchMapping("/{loanId}/periods/{periodId}/state")
    public ResponseEntity<String> updatePeriodState(@PathVariable int loanId, @PathVariable int periodId, @RequestBody StateRequest stateRequest){
        State state = State.valueOf(stateRequest.getState());
        return loanService.updatePeriodState(loanId, periodId, state);
    }
    


}
