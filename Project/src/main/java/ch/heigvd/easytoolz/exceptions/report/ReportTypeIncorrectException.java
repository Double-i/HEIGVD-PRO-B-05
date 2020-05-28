package ch.heigvd.easytoolz.exceptions.report;

public class ReportTypeIncorrectException extends RuntimeException {
    public ReportTypeIncorrectException(){
        super("The type of the report is incorrect");
    }
}
