package ch.heigvd.easytoolz.models;

public enum ReportType {
    racist("racist"),
    sexual("sexual"),
    inappropriate("inappropriate");

    private final String reportType;

    ReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

}
