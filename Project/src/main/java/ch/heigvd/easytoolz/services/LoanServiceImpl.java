package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectAlreadyUsed;
import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.controllers.exceptions.loan.BadParameterException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.State;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.LoanRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository ;

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    UserRepository user;

    @Override
    public ResponseEntity<String> store(Loan newLoan) {

        // Check tool exists
        EZObject obj = ezObjectRepository.findByID(newLoan.getEZObject().getID());
        if (obj == null)
            throw new EZObjectNotFoundException("Object not found " + newLoan.getEZObject() + " ");

        // Check user exists
        User borrower = user.userNameIs(newLoan.getBorrower().getUserName());
        if (borrower == null)
            throw new UserNotFoundException("User not found" + newLoan.getBorrower());

        // Check loan start date is strictly before end date
        if (newLoan.getDateStart().after(newLoan.getDateEnd()) || newLoan.getDateStart().equals(newLoan.getDateEnd()))
            throw new BadParameterException("Invalid parameters");

        // Check tool isn't used for this period
        if (ezObjectRepository.isAlreadyBorrow(obj, newLoan.getDateStart(), newLoan.getDateEnd(), State.accepted))
            throw new EZObjectAlreadyUsed("Invalid date, tool already used");

        // Create loan with pending state
        Loan loan = new Loan(newLoan.getDateStart(), newLoan.getDateEnd(), null, State.pending
                ,newLoan.getBorrower(), newLoan.getEZObject());

        // Save loan
        loanRepository.save(loan);
        return new ResponseEntity<>("The loans has been stored :", HttpStatus.OK);
    }
}
