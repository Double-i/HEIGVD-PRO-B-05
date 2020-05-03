package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.models.DTO.ReportRequest;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.ReportRepository;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    AuthenticationService authService;

    @Autowired
    UserRepository user;

    @Override
    public ResponseEntity<String> store(ReportRequest newReport) {

        // Check tool exists
        EZObject obj = ezObjectRepository.findByID(newReport.getToolId());
        if (obj == null)
            throw new EZObjectNotFoundException("Object not found " + newReport.getToolId() + " ");

        //TODO : Check report type exists

        // Save report
        Report report = new Report(ReportType.valueOf(newReport.getReportType()),obj, authService.getTheDetailsOfCurrentUser(), newReport.getDateReport());

        reportRepository.save(report);

        return new ResponseEntity<>(" {\"status\": \"ok\",\"msg\": \"The report has been stored\"}", HttpStatus.OK);
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


}
