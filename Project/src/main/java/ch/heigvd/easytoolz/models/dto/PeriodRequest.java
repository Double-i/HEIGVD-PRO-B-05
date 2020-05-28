package ch.heigvd.easytoolz.models.dto;

import ch.heigvd.easytoolz.models.State;

import java.util.Date;

/**
 * This class is used to implement the data send via a POST request to add or update a loan period
 */
public class PeriodRequest {
    private Date dateStart;
    private Date dateEnd;
    private State state;

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
}
