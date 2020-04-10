package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    /**
     * Get the list of all the loans
     * @return
     */
    @GetMapping
    public List<Loan> index()
    {
        return loanRepository.findAll();
    }
}
