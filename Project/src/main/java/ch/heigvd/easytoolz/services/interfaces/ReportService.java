package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.DTO.ReportRequest;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    /**
     * Store in db a Report. It checks the date, the availabilty of the tools and the other provided informations
     * @param newReport Loan
     */
    ResponseEntity<String> store(ReportRequest newReport);
}
