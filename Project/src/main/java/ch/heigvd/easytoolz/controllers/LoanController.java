package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.models.*;

import ch.heigvd.easytoolz.models.dto.LoanRequest;
import ch.heigvd.easytoolz.models.dto.PeriodRequest;
import ch.heigvd.easytoolz.models.dto.StateRequest;
import ch.heigvd.easytoolz.repositories.ChatRepository;
import ch.heigvd.easytoolz.repositories.ConversationRepository;
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
    ConversationRepository conversationRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    LoanService loanService;


    /**
     * @return the list of all the loans
     */
    @GetMapping
    public List<Loan> index() {
        return loanRepository.findAll();
    }


    /**
     * Find loans by borrower or user
     * url: /api/loan/find/{username}
     *
     * @param username username of the owner/borrower
     * @param borrower is the user given by "username" borrower ?
     * @param state list of the states of the loan
     * @param city list of the
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
        return loanService.getLoan(username, borrower, state, city, dateStartLess, dateEndLess,dateStartGreater,dateEndGreater);
    }

    /**
     * Add a loan into the database
     * url: POST /api/loans
     *
     * @param newLoan the new loan request to store
     * @return the message returned by LoanService
     */
    @PostMapping
    public ResponseEntity<String> addLoan(@RequestBody LoanRequest newLoan) {
        return loanService.store(newLoan);
    }

    /**
     * Cancel a loan that hasn't been accepted by the tool's owner
     *
     * @param loanId id of the loan to update
     * @return the message returned by LoanService
     */
    @PatchMapping("/{loanId}/state")
    public ResponseEntity<String> updateState(@PathVariable int loanId, @RequestBody StateRequest stateRequest ) {

            State state = State.valueOf(stateRequest.getState());

            return loanService.updateState(loanId,state);
    }

    /**
     * @param loanId id of the loan to update
     * @param periodRequest the request period
     * @return the message returned by LoanService
     */
    @PostMapping("/{loanId}/periods/")
    public ResponseEntity<String> addPeriod(@PathVariable int loanId, @RequestBody PeriodRequest periodRequest){
        return loanService.addPeriod( loanId,  periodRequest);
    }

    /**
     * @param loanId id of the loan to update
     * @param periodId if of the period to update
     * @param stateRequest request of the state
     * @return the message returned by Loan service
     */
    @PatchMapping("/{loanId}/periods/{periodId}/state")
    public ResponseEntity<String> updatePeriodState(@PathVariable int loanId, @PathVariable int periodId, @RequestBody StateRequest stateRequest){
        State state = State.valueOf(stateRequest.getState());
        return loanService.updatePeriodState(loanId, periodId, state);
    }

    /**
     * @param username the username searched
     * @return the loans related to the user which has "username"
     */
    @GetMapping("/{username}")
    public List<Loan> getAllRelatedTo(@PathVariable String username)
    {
        return loanService.getLoansRelatedTo(username);
    }

    /**
     * @param username the username searched
     * @return the conversations related to username
     */
    @GetMapping("/conversations/{username}")
    public List<Conversation> getConversation(@PathVariable String username)
    {
        return conversationRepository.findByOwnerOrBorrower(username,username);
    }

    /**
     * @param conv id of the conversion
     * @param loan id of the loan
     * @return the messages related to loan and the id of the conversion
     */
    @GetMapping("/conversations/{conv}/{loan}/messages/")
    public List<ChatMessage> getMessages( @PathVariable int conv, @PathVariable int loan)
    {
        return chatRepository.findByFkConversation_IDAndFkConversation_Loan(conv,loan);
    }

    /**
     * asks to get back the object to the owner
     * @param loanId id of the loan
     * @return the message returned by LoanService
     */
    @GetMapping("{loanId}/askback")
    public ResponseEntity<String> askToolBack(@PathVariable int loanId){
        return loanService.askBack(loanId);

    }


}
