package ch.heigvd.easytoolz.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;

    @NotNull
    String owner;

    @NotNull
    String borrower;

    @OneToMany(mappedBy = "fkConversation")
    List<ChatMessage> conversation;

    @NotNull
    String convName;

    @NotNull
    @Column(name="fk_loan")
    int loan;


    public String getConvName() {
        return convName;
    }

    public void setConvName(String convName) {
        this.convName = convName;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setConversation(List<ChatMessage> conversation) {
        this.conversation = conversation;
    }

    public String getBorrower()
    {
        return borrower;
    }

    public List<String> getParticipants()
    {
        List<String> list =  new ArrayList<>();
        list.add(owner);
        list.add(borrower);
        return list;

    }
    public int getID() {
        return ID;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public Conversation(){}
    public Conversation(String owner, String borrower, int loan, String convName)
    {
        this.owner = owner;
        this.borrower = borrower;
        this.loan = loan;
        this.convName = convName;
    }
}
