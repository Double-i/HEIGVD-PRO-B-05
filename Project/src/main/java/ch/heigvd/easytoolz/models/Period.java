package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "period")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "dateStart")
    private Date dateStart;

    @NotNull
    @Column(name = "dateEnd")
    private Date dateEnd;

    @NotNull
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @NotNull
    @Column(name = "creator")
    private Creator creator;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fkloan", referencedColumnName = "pkloan")
    private Loan loan;

    public Period() {
    }
    @JsonIgnore
    public Loan getLoan() {
        return loan;
    }

    public Period(Date dateStart, Date dateEnd, State state, Creator creator, Loan loan ) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = state;
        this.creator = creator;
        this.loan = loan;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "Period{" +
                "id=" + id +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", state=" + state +
                ", creator=" + creator +
                '}';
    }


}
