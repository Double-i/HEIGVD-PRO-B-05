package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.DTO.ReportRequest;
import ch.heigvd.easytoolz.models.Report;
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
     * Add a Report into the database
     * url: POST /api/reports
     *
     * @param newReport
     * @return
     */
    @PostMapping
    public ResponseEntity<String> addReport(@RequestBody ReportRequest newReport) {
        return reportService.store(newReport);
    }
}
