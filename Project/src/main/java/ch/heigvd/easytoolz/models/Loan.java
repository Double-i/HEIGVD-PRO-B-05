package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="loan")
public class Loan {

    public int getPkLoan() {
        return pkLoan;
    }
    public String getDateStart() {
        return dateStart;
    }
    public String getDateEnd() {
        return dateEnd;
    }
    public String getDateReturn() {
        return dateReturn;
    }
    public State getState() {
        return state;
    }
    public User getBorrower() {
        return borrower;
    }
    public EZObject getObject() { return object; }

    public void setPkLoan(int pkLoan) {
        this.pkLoan = pkLoan;
    }
    public void setdateStart(String dateStart) {
        this.dateStart = dateStart;
    }
    public void setdateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }
    public void setObject(EZObject object) {
        this.object = object;
    }

    @Id
    @Column(name="pkloan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pkLoan;

    @Column(name="datestart")
    @NotNull
    private String dateStart;

    @Column(name="dateend")
    @NotNull
    private String dateEnd;

    @Column(name="datereturn")
    private String dateReturn;

    @Column(name="state")
    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "fkezobject", referencedColumnName = "id")
    private EZObject object;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "borrower", referencedColumnName = "userName")
    private User borrower;

    public Loan(){}

    public Loan(String dateStart,String dateEnd, String dateReturn, State state, User borrower, EZObject object) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateReturn = dateReturn;
        this.state = state;
        this.borrower = borrower;
        this.object = object;
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
                ", object='" + object + '\'' +
                "}\n";
    }


}
