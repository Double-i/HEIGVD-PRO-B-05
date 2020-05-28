package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.dto.ReportRequest;
import ch.heigvd.easytoolz.models.Report;

import java.util.List;

public interface ReportService {

    /**
     * Store in db a Report. It checks the date, the availabilty of the tools and the other provided informations
     * @param newReport Report
     */
    Report store(ReportRequest newReport);

    /**
     * Get reports where the user is signaled (as a reported or reporter user)
     * @param username
     * @param reported
     * @return
     */
    List<Report> getReport(String username, Boolean reported);
}

