package ch.heigvd.easytoolz.models.dto;

import java.util.Date;

public class ReportRequest {

    private String reportType;
    private  int toolId;
    private Date dateReport;

    public String getReportType() { return reportType;}
    public int getToolId() {
        return toolId;
    }
    public Date getDateReport() {
        return dateReport;
    }

}
