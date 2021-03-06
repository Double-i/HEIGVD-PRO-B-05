package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedException;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectAlreadyUsed;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.exceptions.loan.LoanInvalidParameterException;
import ch.heigvd.easytoolz.exceptions.loan.LoanInvalidUserException;
import ch.heigvd.easytoolz.exceptions.loan.LoanPeriodAlreadyPassedException;
import ch.heigvd.easytoolz.exceptions.loan.LoanStateCantBeUpdatedException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.models.dto.LoanRequest;
import ch.heigvd.easytoolz.models.dto.PeriodRequest;
import ch.heigvd.easytoolz.repositories.*;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.LoanService;
import ch.heigvd.easytoolz.services.interfaces.MailService;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.specifications.LoanSpecs;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;


@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository user;

    @Override
    public ResponseEntity<String> store(LoanRequest newLoan) {


        if (authService.getTheDetailsOfCurrentUser() == null)
            throw new AccessDeniedException();

        // Check tool exists
        EZObject obj = ezObjectRepository.findByID(newLoan.getToolId());
        if (obj == null)
            throw new EZObjectNotFoundException("Object not found " + newLoan.getToolId() + " ");

        // Check loan start date is strictly before end date
        if (!isValidDate(newLoan.getDateStart(), newLoan.getDateEnd()))
            throw new LoanInvalidParameterException("Invalid parameters");

        // Check tool isn't used for this period
        if (ezObjectRepository.isAlreadyBorrow(obj, newLoan.getDateStart(), newLoan.getDateEnd(), State.accepted, State.accepted))
            throw new EZObjectAlreadyUsed("Invalid date, tool already used");


        // Save loan

        User currentUser = authService.getTheDetailsOfCurrentUser();

        Loan loan = new Loan(null, State.pending, currentUser, obj);
        loanRepository.save(loan);

        // The owner is notified that someone wants borrow his object
        notificationService.storeNotification(
                ServiceUtils.createNotification(
                        StateNotification.RESERVATION,
                        obj.getOwner(),
                        currentUser.getUserName(), obj.getName()));

        Period period = new Period(newLoan.getDateStart(), newLoan.getDateEnd(), State.accepted, Creator.borrower, loan);

        periodRepository.save(period);

        return new ResponseEntity<>("{\"status\": \"ok\",\"msg\": \"The loans has been stored\"}", HttpStatus.OK);
    }

    /**
     * Update the state of a loan  of the given loan with the given state
     *
     * @param loanId   the id of the loan we want to update the state
     * @param newState the new state
     * @return
     */
    @Override
    public ResponseEntity<String> updateState(int loanId, State newState) {

        // Get loan by id and check if exists
        Loan loan = loanRepository.getOne(loanId);

        // Check the state. It has to be pending state to be : cancel - accepted or refused
        if (!loan.getState().equals(State.pending) && !loan.getState().equals(State.accepted))
            throw new LoanStateCantBeUpdatedException("Loan has already been accepted/refused/canceled");

        // Check the loan hasn't started yet
        if (!loan.getValidPeriod().getDateStart().after(new Date()))
            throw new LoanStateCantBeUpdatedException("Loan start date has already been passed");

        boolean done = false;

        StateNotification notifState = null;
        User recipient = null;
        String messageParam = null;

        switch (newState) {
            case accepted:
                Conversation conv = new Conversation(loan.getOwner().getUserName(), loan.getBorrower().getUserName(), loan.getPkLoan(), loan.getEZObject().getName());
                conversationRepository.save(conv);
                done = updateLoanStateByOwner(loan, newState);
                notifState = StateNotification.ACCEPTATION_DEMANDE_EMPRUNT;
                recipient = loan.getBorrower();
                messageParam = loan.getEZObject().getName();
                break;
            case refused:
                done = updateLoanStateByOwner(loan, newState);
                notifState = StateNotification.REFUS_DEMANDE_EMPRUNT;
                recipient = loan.getBorrower();
                messageParam = loan.getEZObject().getName();

                break;
            case cancel:
                done = cancelLoan(loan);
                if (done) {
                    if (isOwner(loan)) {
                        notifState = StateNotification.ANNULATION_RESERVATION_BORROWER;
                        recipient = loan.getBorrower();
                        messageParam = loan.getEZObject().getName();
                    } else {
                        notifState = StateNotification.ANNULATION_RESERVATION_OWNER;
                        recipient = loan.getEZObject().getOwner();
                        messageParam = loan.getEZObject().getName();
                    }
                }
                break;
            default:
                throw new LoanStateCantBeUpdatedException("Cannot update loan");
        }
        if (!done)
            throw new RuntimeException("LoanService - Something went wrong while trying to update loan state");
        else
            notificationService.storeNotification(ServiceUtils.createNotification(
                    notifState,
                    recipient,
                    messageParam
            ));

        return new ResponseEntity<>("{\"status\": \"ok\",\"msg\": \"The loans has been\"}", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addPeriod(int loanId, PeriodRequest periodRequest) {
        // Check loan exists and get it
        Loan loan = loanRepository.getOne(loanId);
        Creator creator = getRole(loan);

        // Check period date
        if (!isValidDate(periodRequest.getDateStart(), periodRequest.getDateEnd()))
            throw new LoanInvalidParameterException("Invalid parameters");

        // Check that the loan isn't passed and the new end date isn't passed too
        Date now = new Date();
        if (!loan.getValidPeriod().getDateEnd().after(now)
                || !periodRequest.getDateEnd().before(loan.getValidPeriod().getDateEnd())
                || !periodRequest.getDateEnd().after(now)) {
            throw new LoanInvalidParameterException("Cannot update this loan");
        }

        // Invalid other period of the borrower/owner
        for (Period period : loan.getPendingPeriods()) {
            if (period.getCreator().equals(creator)) {
                period.setState(State.cancel);
                periodRepository.save(period);
            }
        }
        StateNotification stateNotification;
        User recipient;
        EZObject obj = loan.getEZObject();
        if (creator.equals(Creator.borrower)) {
            recipient = obj.getOwner();
            stateNotification = StateNotification.RACCOURCISSEMENT_OWNER;
        } else {
            recipient = loan.getBorrower();
            stateNotification = StateNotification.RACCOURCISSEMENT_BORROWER;

        }
        notificationService.storeNotification(
                ServiceUtils.createNotification(
                        stateNotification,
                        recipient, obj.getName()));

        // Save
        Period newPeriod = new Period(periodRequest.getDateStart(), periodRequest.getDateEnd(), State.pending, creator, loan);
        periodRepository.save(newPeriod);

        return new ResponseEntity<>("{\"status\": \"ok\",\"msg\": \"Period added\", \"id\": " + newPeriod.getId() + "}", HttpStatus.OK);
    }

    /**
     * Update the state of a loan. Check that the user has the right to update (only the period creator can )
     *
     * @param loanId   loan id
     * @param periodId period id
     * @param newState the new state
     * @return ResponseEntity
     * @throws InvalidParameterException can be thrown when the given params are wrong
     */
    @Override
    public ResponseEntity<String> updatePeriodState(int loanId, int periodId, State newState) throws InvalidParameterException {

        Loan loan = loanRepository.getOne(loanId);
        Period toUpdatePeriod = periodRepository.getOne(periodId);
        if (toUpdatePeriod.getLoan().getPkLoan() != loan.getPkLoan())
            throw new LoanInvalidParameterException("Period doesn't exist for this loan");


        if (!toUpdatePeriod.getState().equals(State.pending)) {
            throw new LoanInvalidParameterException("Period cannot be update");
        }

        // Check that the period has been created by the opposite Role and the new period isn't passed
        if (!toUpdatePeriod.getDateEnd().after(new Date()))
            throw new LoanPeriodAlreadyPassedException("Loan : period already passed");


        Creator creator = getRole(loan);

        // Check the user has the appropriate right to update the state
        if (newState.equals(State.cancel)) {
            if (!toUpdatePeriod.getCreator().equals(creator))
                throw new LoanInvalidUserException("no right to do this");

        } else {
            if (toUpdatePeriod.getCreator().equals(creator))
                throw new LoanInvalidUserException("no right to do this");
            if (newState.equals(State.accepted)) {
                // Set state to refused for the other pending periods
                for (Period period : loan.getPeriods()) {
                    period.setState(State.refused);
                    periodRepository.save(period);
                }
            }
            EZObject obj = loan.getEZObject();
            User recipient;
            StateNotification stateNotification;

            if (isBorrower(loan)) {
                recipient = obj.getOwner();
                if (newState.equals(State.accepted)) {
                    stateNotification = StateNotification.ACCEPTATION_DEMANDE_RACOURCISSEMENT_OWNER;
                } else {
                    stateNotification = StateNotification.REFUS_DEMANDE_RACOURCISSEMENT_OWNER;
                }
            } else {
                recipient = loan.getBorrower();
                if (newState.equals(State.accepted)) {
                    stateNotification = StateNotification.ACCEPTATION_DEMANDE_RACOURCISSEMENT_BORROWER;
                } else {
                    stateNotification = StateNotification.REFUS_DEMANDE_RACOURCISSEMENT_BORROWER;
                }
            }

            notificationService.storeNotification(
                    ServiceUtils.createNotification(
                            stateNotification,
                            recipient, obj.getName()));
        }
        toUpdatePeriod.setState(newState);
        periodRepository.save(toUpdatePeriod);

        return new ResponseEntity<>("{\"status\": \"ok\",\"msg\": \"Période mis à jour\"}", HttpStatus.OK);
    }

    /**
     * Get loans of a borrower or owner, it's possible to apply filters in the search
     *
     * @param username
     * @param borrower
     * @param state
     * @param city
     * @param dateStartLess
     * @param dateEndLess
     * @param dateStartGreater
     * @param dateEndGreater
     * @return
     */
    @Override
    public List<Loan> getLoan(String username, boolean borrower, List<String> state, List<String> city, Date dateStartLess, Date dateEndLess,
                              Date dateStartGreater, Date dateEndGreater) {


        Specification<Loan> specs;

        if (borrower) {
            specs = LoanSpecs.getLoanByBorrower(username);
        } else {
            specs = LoanSpecs.getLoanByOwner(username);
        }

        if (state != null) {
            Specification<Loan> states = LoanSpecs.getState(state.get(0));

            for (int i = 1; i < state.size(); ++i) {
                states = states.or(LoanSpecs.getState(state.get(i)));

            }
            specs = specs.and(states);

        }

        if (city != null) {
            Specification<Loan> cities = LoanSpecs.getCity(city.get(0));
            for (int i = 1; i < city.size(); ++i) {
                cities = cities.or(LoanSpecs.getCity(city.get(i)));
            }
            specs = specs.and(cities);
        }

        if (dateStartLess != null)
            specs = specs.and(LoanSpecs.getDateStartLess(dateStartLess));

        if (dateEndLess != null)
            specs = specs.and(LoanSpecs.getDateEndLess(dateEndLess));

        if (dateStartGreater != null)
            specs = specs.and(LoanSpecs.getDateStartGreater(dateStartGreater));

        if (dateEndGreater != null)
            specs = specs.and(LoanSpecs.getDateEndGreater(dateEndGreater));

        return loanRepository.findAll(specs);
    }

    @Override
    public boolean isObjectIsCurrentlyBorrowed(int objectId) {

        Date currentDate = new Date();

        Specification<Loan> specs = null;

        Specification<Loan> start = LoanSpecs.getDateStartLess(currentDate);
        Specification<Loan> end = LoanSpecs.getDateEndGreater(currentDate);
        Specification<Loan> timespan = start.and(end);


        specs = LoanSpecs.getObject(objectId);
        specs = specs.and(timespan);

        List<Loan> loans = loanRepository.findAll(specs);

        return loans.size() > 0;
    }

    @Override
    public List<Loan> getLoansRelatedTo(String username) {
        Specification<Loan> byUser = null;

        byUser = LoanSpecs.getLoanByOwner(username);
        byUser = byUser.or(LoanSpecs.getLoanByBorrower(username));

        Specification<Loan> byState = null;
        byState = LoanSpecs.getState(State.accepted.getState());

        Specification<Loan> finalSpec = byUser.and(byState);
        return loanRepository.findAll(finalSpec);

    }

    @Override
    public ResponseEntity<String> askBack(int loanId) {
        Loan loan = loanRepository.getOne(loanId);

        if (!isOwner(loan))
            throw new AccessDeniedException();

        User borrower = loan.getBorrower();
        User owner = loan.getOwner();
        EZObject object = loan.getEZObject();

        // Send a notification
        notificationService.storeNotification(ServiceUtils
                .createNotification(StateNotification.DEMANDE_RETOUR, borrower, object.getName(), owner.getUserName()));

        // Send an email.
        mailService.sendSimpleMessage(borrower.getEmail(), "Vous n'avez pas rendu un outil",
                String.format("Vous n'avez pas rendu l'outil %s appartenant à %s (%s). Veuillez le contacter " +
                                "le plus rapidement possible pour ne pas avoir de sanction", object.getName(),
                        owner.getUserName(), owner.getEmail()));

        return new ResponseEntity<>("{\"status\": \"ok\",\"msg\": \"Ask back ok\"}", HttpStatus.OK);
    }

    /**
     * Update the status to accept or refused of the given loan
     *
     * @param loan  the loan to update the state
     * @param state the new state
     * @return return true if the update has been done
     */
    private boolean updateLoanStateByOwner(Loan loan, State state) {
        boolean updated = false;
        if (isOwner(loan)) {
            if (state.equals(State.accepted)) {
                List<Loan> pendingLoans = ezObjectRepository.overlapLoans(loan.getEZObject(), loan,
                        loan.getValidPeriod().getDateStart(), loan.getValidPeriod().getDateEnd(), State.pending, State.accepted);
                for (Loan currentLoan : pendingLoans) {

                    currentLoan.setState(State.refused);
                    loanRepository.save(currentLoan);
                    notificationService.storeNotification(ServiceUtils.createNotification(
                            StateNotification.REFUS_DEMANDE_EMPRUNT,
                            currentLoan.getBorrower(),
                            currentLoan.getEZObject().getName()));

                }
            }
            loan.setState(state);
            loanRepository.save(loan);
            updated = true;
        }
        return updated;
    }

    /**
     * Cancel the given loan.
     * <p>
     * It checks that the current logged in user is either the borrower or owner of the loan
     *
     * @param loan the loan to
     * @return return true if the loan has been cancel
     */
    private boolean cancelLoan(Loan loan) {
        boolean updated = false;
        if (isBorrower(loan) || isOwner(loan)) {
            loan.setState(State.cancel);
            loanRepository.save(loan);
            updated = true;
        }
        return updated;
    }

    /**
     * Return true if the logged in user is the borrower of the given loan
     *
     * @param loan the loan to check
     * @return
     */
    private boolean isBorrower(Loan loan) {
        User currentUser = authService.getTheDetailsOfCurrentUser();
        return currentUser != null && currentUser.getUserName().equals(loan.getBorrower().getUserName());

    }

    /**
     * Return true if the logged in user is the owner of the tool of the given loan
     *
     * @param loan the loan to check
     * @return
     */
    private boolean isOwner(Loan loan) {
        User currentUser = authService.getTheDetailsOfCurrentUser();
        return currentUser != null && currentUser.getUserName().equals(loan.getEZObject().getOwner().getUserName());
    }

    private boolean isValidDate(Date dateStart, Date dateEnd) {
        return dateStart.before(dateEnd);
    }

    private Creator getRole(Loan loan) throws LoanInvalidUserException {
        // Check that the current user is either the borrower or the owner otherwise
        Creator creator;
        if (isBorrower(loan)) {
            creator = Creator.borrower;
        } else if (isOwner(loan)) {
            creator = Creator.owner;
        } else {
            throw new LoanInvalidUserException("You've no right to edit period of this loan");
        }
        return creator;
    }
}

