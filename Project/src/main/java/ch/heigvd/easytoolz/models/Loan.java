package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="loan")
public class Loan {

    public int getPkLoan() {
        return pkLoan;
    }

    public Date getDateReturn() {
        return dateReturn;
    }
    public State getState() {
        return state;
    }
    public User getBorrower() {
        return borrower;
    }
    public EZObject getEZObject() {
        return EZObject;
    }
    public List<Period> getPeriods() {
        return periods;
    }
    public Period getValidPeriod(){
        Period validPeriod = null;
        for(Period period : periods){
            if(period.getState().equals(State.accepted)){
                validPeriod = period;
                break;
            }
        }
        return validPeriod;
    }
    // TODO à voir si utile
    public List<Period> getPendingPeriods(){
        List<Period> pendingPeriod = new ArrayList<>();
        for(Period period : periods){
            if(period.getState().equals(State.pending)){
                pendingPeriod.add(period);
            }
        }
        return pendingPeriod;
    }


    public void setPkLoan(int pkLoan) {
        this.pkLoan = pkLoan;
    }
    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }
    public void setEZObject(EZObject EZObject) {
        this.EZObject = EZObject;
    }
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public String getOwner(){return this.EZObject.getOwnerUserName();}

    @Id
    @Column(name="pkloan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pkLoan;

    @Column(name="datereturn")
    private Date dateReturn;

    @Column(name="state")
    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    @JoinColumn(name="borrower", referencedColumnName = "username")
    private User borrower;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    @JoinColumn(name="EZObject", referencedColumnName = "ID")
    private EZObject EZObject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loan")
    private List<Period> periods;


    public Loan(){}

    public Loan( Date dateReturn, State state, User borrower, EZObject EZObject ) {
        this.dateReturn = dateReturn;
        this.state = state;
        this.borrower = borrower;
        this.EZObject = EZObject;

    }
}
