package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    EZObjectRepository ezObjectRepository;

    /**
     * Get the list of all the loans
     * @return
     */
    @GetMapping
    public List<Loan> index()
    {
        return loanRepository.findAll();
    }

    /**
     * Find loans by owner
     * url: api/loans/find/{username}
     * @param username
     * @return
     */

    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<Loan> getLoanByBorrower(@PathVariable String username)
    {

        //if(loanRepository.findByOwner_UserName(username).size() == 0)
            //throw new EZObjectNotFoundException("No loans where found for user "+username);
        return loanRepository.findByBorrower(username);
    }

    /**
     * Add a loan into the database
     * @param newLoan
     * @return
     */
    @PostMapping("/add")
    public Loan addLoan(@RequestBody Loan newLoan)
    {
        // Vérérification
        //
        EZObject obj = ezObjectRepository.findByID(newLoan.getfkEZObject());
        if(obj == null)
            throw new EZObjectNotFoundException("Object not found " + newLoan.getfkEZObject() + " ");


        Loan loan = new Loan(newLoan.getDateStart(),newLoan.getDateEnd(),newLoan.getDateReturn(),newLoan.getState(),newLoan.getBorrower(),newLoan.getfkEZObject());
        return loanRepository.save(loan);
    }
}
