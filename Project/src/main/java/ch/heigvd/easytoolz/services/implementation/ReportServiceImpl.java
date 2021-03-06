package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.exceptions.report.ReportTypeIncorrectException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.models.dto.ReportRequest;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.ReportRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.NotificationService;
import ch.heigvd.easytoolz.services.interfaces.ReportService;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EZObjectRepository ezObjectRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserRepository user;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Report store(ReportRequest newReport) {

        // Check tool exists
        EZObject obj = ezObjectRepository.findByID(newReport.getToolId());
        if (obj == null)
            throw new EZObjectNotFoundException("Object not found " + newReport.getToolId() + " ");

        String nameReportType = newReport.getReportType();

        if(!checkReportType(nameReportType))
            throw new ReportTypeIncorrectException();

        // Save report
        Report report = new Report(ReportType.valueOf(newReport.getReportType()),obj, authService.getTheDetailsOfCurrentUser());

        report = reportRepository.save(report);

        notificationService.storeNotification(ServiceUtils.createNotification(
                StateNotification.SIGNALEMENT,
                report.getEZObject().getOwner(),
                report.getEZObject().getName(),
                report.getReportType().getReportType()
        ));

        return report;
    }

    @Override
    public List<Report> getReport(String username, Boolean reported) {

        if(reported) {
            return reportRepository.findReportByEZObject_Owner_UserName(username);
        }
        else{
            return reportRepository.findReportByReporter_UserName(username);
        }
    }

    private boolean checkReportType(String nameReportType){
        for(ReportType reportType : ReportType.values()){
            if(reportType.getReportType().equals(nameReportType)){
                return true;
            }
        }
        return false;
    }

}
