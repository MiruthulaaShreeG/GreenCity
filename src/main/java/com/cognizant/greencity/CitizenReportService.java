package com.cognizant.greencity;

import com.cognizant.greencity.dao.CitizenReportRepository;
import com.cognizant.greencity.entity.CitizenReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitizenReportService {

    @Autowired
    private CitizenReportRepository citizenReportRepository;

    // File a new sustainability report
    public CitizenReport fileReport(CitizenReport report) {
        report.setDate(LocalDateTime.now());
        // Default status for new reports
        if (report.getStatus() == null) {
            report.setStatus("PENDING_REVIEW");
        }
        return citizenReportRepository.save(report);
    }

    // Get all reports for City Planner monitoring [cite: 85]
    public List<CitizenReport> getAllReports() {
        return citizenReportRepository.findAll();
    }

    // Update report status (e.g., when a Planner starts investigation)
    public CitizenReport updateReportStatus(long reportId, String newStatus) {
        CitizenReport report = citizenReportRepository.findById(reportId).orElse(null);
        if (report != null) {
            report.setStatus(newStatus);
            return citizenReportRepository.save(report);
        }
        return null;
    }
}