package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.Loan;

import ch.heigvd.easytoolz.models.State;
import ch.heigvd.easytoolz.models.StateRequest;
import ch.heigvd.easytoolz.repositories.LoanRepository;
import ch.heigvd.easytoolz.services.LoanService;
import ch.heigvd.easytoolz.specifications.LoanSpecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
                                    @RequestParam(required = false) boolean pending,
                                    @RequestParam(required = false) boolean refused,
                                    @RequestParam(required = false) boolean accepted,
                                    @RequestParam(required = false) boolean cancel)
    {

    return loanService.getLoan(username, borrower, pending, refused, accepted, cancel);

    }

    /**
     * Add a loan into the database
     * url: POST /api/loans
     *
     * @param newLoan
     * @return
     */
    @PostMapping
    public ResponseEntity<String> addLoan(@RequestBody Loan newLoan) {
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
            System.out.println(state);
            return loanService.updateState(loanId,state);



    }
}
