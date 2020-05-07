package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="report")
public class Report {

    public int getPkReport(){return pkReport;}

    public ReportType getReportType() {
        return reportType;
    }

    public EZObject getEZObject() {
        return EZObject;
    }

    public User getReporter() {
        return reporter;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public boolean getAccepted() { return accepted; }

    private void setReportType(ReportType reportType){
        this.reportType = reportType;
    }

    private void setEZObject(EZObject object){this.EZObject = object;}

    private void setReporter(User reporter){
        this.reporter = reporter;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public void setAccepted(boolean accepted) {this.accepted = accepted; }

    @Id
    @Column(name="pkreport")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pkReport;

    @Column(name="type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    @JoinColumn(name="EZObject", referencedColumnName = "ID")
    private EZObject EZObject;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    @JoinColumn(name="reporter", referencedColumnName = "username")
    private User reporter;

    @Column(name="datereport")
    private Date dateReport;

    @Column(name="accepted")
    private boolean accepted = false;

    @Column(name="valid")
    private boolean valid = false;

    public Report() {}

    public Report(ReportType reportType, EZObject EZObject, User reporter, Date dateReport){
        this.reportType = reportType;
        this.EZObject = EZObject;
        this.reporter = reporter;
        this.dateReport = dateReport;
    }
}
