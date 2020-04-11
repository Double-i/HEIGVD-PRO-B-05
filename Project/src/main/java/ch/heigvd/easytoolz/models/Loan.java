package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
    public String getState() {
        return state;
    }
    public String getBorrower() {
        return borrower;
    }
    public int getfkEZObject() {
        return fkEZObject;
    }

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
    public void setState(String state) {
        this.state = state;
    }
    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
    public void setFkEZObject(int fkEZObject) {
        this.fkEZObject = fkEZObject;
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
    private String state;

    @Column(name="borrower")
    @NotNull
    private String borrower;

    @Column(name="fkezobject")
    @NotNull
    private int fkEZObject;


    public Loan(){}

    public Loan(String dateStart,String dateEnd, String dateReturn, String state, String borrower, int fkEZObject) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateReturn = dateReturn;
        this.state = state;
        this.borrower = borrower;
        this.fkEZObject = fkEZObject;
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
                ", fkEZObject='" + fkEZObject + '\'' +
                "}\n";
    }


}
