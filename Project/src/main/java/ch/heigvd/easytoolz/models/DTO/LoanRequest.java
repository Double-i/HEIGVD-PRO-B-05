package ch.heigvd.easytoolz.models.DTO;

import java.util.Date;

public class LoanRequest {
    private Date dateStart;
    private Date dateEnd;
    private int toolId;

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int getToolId() {
        return toolId;
    }
}
