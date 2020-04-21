package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.models.*;

import ch.heigvd.easytoolz.models.DTO.LoanRequest;
import ch.heigvd.easytoolz.models.DTO.PeriodRequest;
import ch.heigvd.easytoolz.models.DTO.StateRequest;
import ch.heigvd.easytoolz.repositories.LoanRepository;
import ch.heigvd.easytoolz.services.interfaces.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
     * @param borrower
     * @param state
     * @param city
     * @param dateStartLess
     * @param dateEndLess
     * @return
     */
    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<Loan> getLoanByUser(@PathVariable String username, @RequestParam boolean borrower,
                                    @RequestParam(required = false) List<String> state,
                                    @RequestParam(required = false) List<String> city,
                                    @RequestParam(required = false, name = "startLT") @DateTimeFormat(pattern="yyyy-MM-dd")  Date dateStartLess,
                                    @RequestParam(required = false, name = "endLT") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateEndLess,
                                    @RequestParam(required = false, name = "startGT") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateStartGreater,
                                    @RequestParam(required = false, name = "endGT") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateEndGreater)
    {
        System.out.println("borrower: "+borrower);
        System.out.println("startLT "+dateStartLess);
        System.out.println("endLT "+dateEndLess);
        System.out.println("startGT "+dateStartGreater);
        System.out.println("endGT "+dateEndGreater);
        System.out.println("-----");

        return loanService.getLoan(username, borrower, state, city, dateStartLess, dateEndLess,dateStartGreater,dateEndGreater);
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
