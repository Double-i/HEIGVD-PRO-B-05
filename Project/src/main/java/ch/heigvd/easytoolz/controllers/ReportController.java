package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.DTO.ReportRequest;
import ch.heigvd.easytoolz.models.Report;
import ch.heigvd.easytoolz.models.ReportType;
import ch.heigvd.easytoolz.models.json.SuccessResponse;
import ch.heigvd.easytoolz.repositories.ReportRepository;
import ch.heigvd.easytoolz.services.interfaces.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportService reportService;

    /**
     * Get the list of all the reports
     *
     * @return
     */
    @GetMapping
    public List<Report> index() {
        return reportRepository.findAll();
    }

    /**
     * Get the list of all the reports who the user signaled is the reporter or the reported
     *
     * @return
     */
    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<Report> findByReported(@PathVariable String username, @RequestParam(required = false, defaultValue = "true") boolean reported) {
        return reportService.getReport(username,reported);
    }

    @GetMapping("/find/type/{type}")
    @ResponseBody
    public List<Report> findByType(@PathVariable String type) {
        return reportRepository.findReportByReportType_OrderByEZObject(ReportType.valueOf(type));
    }

    /**
     * Add a Report into the database
     * url: POST /api/reports
     *
     * @param newReport
     * @return
     */
    @PostMapping
    public ResponseEntity<SuccessResponse> addReport(@RequestBody ReportRequest newReport) {
        reportService.store(newReport);
        return ResponseEntity.ok(new SuccessResponse("The report has been stored"));
    }
}
