package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;


@Entity
@Table(name="loan")
public class Loan {

    public int getPkLoan() {
        return pkLoan;
    }
    public Date getDateStart() {
        return dateStart;
    }
    public Date getDateEnd() {
        return dateEnd;
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

    public void setPkLoan(int pkLoan) {
        this.pkLoan = pkLoan;
    }
    public void setdateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public void setdateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    @Id
    @Column(name="pkloan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pkLoan;

    @Column(name="datestart")
    @NotNull
    @Future
    private Date dateStart;

    @Column(name="dateend")
    @NotNull
    @Future
    private Date dateEnd;

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

    public Loan(){}

    public Loan(Date dateStart,Date dateEnd, Date dateReturn, State state, User borrower, EZObject EZObject) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateReturn = dateReturn;
        this.state = state;
        this.borrower = borrower;
        this.EZObject = EZObject;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "pkLoan=" + pkLoan +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", dateReturn='" + dateReturn + '\'' +
                ", state='" + state + '\'' +
                ", borrower='" + borrower + '\'' +
                ", EZObject='" + EZObject + '\'' +
                "}\n";
    }


}
