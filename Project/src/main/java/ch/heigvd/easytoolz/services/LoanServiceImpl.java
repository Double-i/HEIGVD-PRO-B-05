package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectAlreadyUsed;
import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.controllers.exceptions.loan.BadParameterException;
import ch.heigvd.easytoolz.controllers.exceptions.loan.LoanStateCantBeUpdatedException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.State;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.LoanRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.specifications.LoanSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    AuthenticationService authService;

    @Autowired
    UserRepository user;

    @Override
    public ResponseEntity<String> store(Loan newLoan) {

        // TODO check the logged in user instead of the given user.
        // Check tool exists
        EZObject obj = ezObjectRepository.findByID(newLoan.getEZObject().getID());
        if (obj == null)
            throw new EZObjectNotFoundException("Object not found " + newLoan.getEZObject() + " ");


        // Check loan start date is strictly before end date
        if (newLoan.getDateStart().after(newLoan.getDateEnd()) || newLoan.getDateStart().equals(newLoan.getDateEnd()))
            throw new BadParameterException("Invalid parameters");

        // Check tool isn't used for this period
        if (ezObjectRepository.isAlreadyBorrow(obj, newLoan.getDateStart(), newLoan.getDateEnd(), State.accepted))
            throw new EZObjectAlreadyUsed("Invalid date, tool already used");

        // Create loan with pending state
        Loan loan = new Loan(newLoan.getDateStart(), newLoan.getDateEnd(), null, State.pending
                , newLoan.getBorrower(), newLoan.getEZObject());

        // Save loan
        loanRepository.save(loan);
        return new ResponseEntity<>("The loans has been stored :", HttpStatus.OK);
    }

    /**
     * Update the state of a state which we gave the id with the given new state.
     *
     * @param loanId the id of the loan we want to update the state
     * @param newState the new state
     * @return
     */
    @Override
    public ResponseEntity<String> updateState(int loanId, State newState) {

        // Get loan by id and check if exists
        Loan loan = loanRepository.getOne(loanId); // it return

        // Check the state. It has to be pending state to be : cancel - accepted or refused
        System.out.println(newState);
        State state = State.accepted;


        if(!loan.getState().equals( State.pending))
            throw new LoanStateCantBeUpdatedException("Loan has already been accepted/refused/canceled");

        // Check the loan hasn't started yet
        if(!loan.getDateStart().after( new Date()))
            throw new LoanStateCantBeUpdatedException("Loan start date has already been passed");

        boolean done = false;

        System.out.println(newState);

        switch(newState){
            case accepted:
            case refused:
                done = updateLoanStateByOwner(loan, newState);
                break;
            case cancel:
                System.out.println("testteststest");
                done = cancelLoan(loan);
                break;
        }
        if(!done)
            throw new RuntimeException("LoanService - Something went wrong while trying to update loan state");

        return new ResponseEntity<>("The loans has been ", HttpStatus.OK);
    }

    /**
     * Get loans of a borrower or owner, it's possible to apply filters in the search (for the moment, only with "state" attributes)
     *
     * @param username
     * @param borrower
     * @param pending
     * @param refused
     * @param accepted
     * @param cancel
     * @return
     */
    @Override
    public List<Loan> getLoan(String username, boolean borrower, boolean pending, boolean refused, boolean accepted, boolean cancel) {
        if(loanRepository.findByBorrower_UserName(username).size() == 0)
            throw new EZObjectNotFoundException("No loans where found for user "+username);

        Specification<Loan> specs;

        if(borrower) {
            specs = Specification.where(LoanSpecs.getLoanByBorrower(username));
        }
        else{
            specs = Specification.where(LoanSpecs.getLoanByOwner(username));
        }

        if(pending)
            specs = specs.and(LoanSpecs.getPendingLoan());

        if(refused)
            specs = specs.and(LoanSpecs.getRefusedLoan());

        if(accepted)
            specs = specs.and(LoanSpecs.getAcceptedLoan());

        if(cancel)
            specs = specs.and(LoanSpecs.getCancelLoan());

        return loanRepository.findAll(specs);
    }

    /**
     * Update the status to accept or refused of the given loan
     * @param loan the loan to update the state
     * @param state the new state
     * @return return true if the update has been done
     */
    private boolean updateLoanStateByOwner(Loan loan, State state){
        boolean updated = false;
        if(isOwner(loan)){
            loan.setState(state);
            loanRepository.save(loan);
            updated = true;
        }
        return updated;
    }

    /**
     * Cancel the given loan.
     *
     * It checks that the current logged in user is the borrower of the loan
     * @param loan the loan to
     * @return return true if the loan has been cancel
     */
    private boolean cancelLoan(Loan loan){
        boolean updated = false;
        if(isBorrower(loan)){
            loan.setState(State.cancel);
            loanRepository.save(loan);
            updated = true;
        }
        return updated;
    }

    /**
     * Return true if the logged in user is the borrower of the given loan
     * @param loan the loan to check
     * @return
     */
    private boolean isBorrower(Loan loan){
        User currentUser = authService.getTheDetailsOfCurrentUser();
        return currentUser != null && currentUser.getUserName().equals(loan.getBorrower().getUserName());

    }

    /**
     * Return true if the logged in user is the owner of the tool of the given loan
     * @param loan the loan to check
     * @return
     */
    private boolean isOwner(Loan loan){
        User currentUser = authService.getTheDetailsOfCurrentUser();
        return currentUser != null && currentUser.getUserName().equals(loan.getEZObject().getOwner().getUserName());
    }

}


