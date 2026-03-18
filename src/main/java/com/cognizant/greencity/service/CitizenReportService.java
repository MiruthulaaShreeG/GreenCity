package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.CitizenReportDTO;
import java.util.List;

public interface CitizenReportService {
    /**
     * File a new report (Create)
     * Default status: PENDING
     * Triggers notification creation
     */
    CitizenReportDTO fileReport(CitizenReportDTO reportDto);
    
    /**
     * Get all reports (Read)
     */
    List<CitizenReportDTO> getAllReports();
    
    /**
     * Get all reports by citizen ID
     * @throws CitizenReportNotFound if no reports found
     */
    List<CitizenReportDTO> getReportsByCitizenId(Long citizenId);
    
    /**
     * Get report by report ID
     * @throws CitizenReportNotFound if report not found
     */
    CitizenReportDTO getReportById(Long reportId);
    
    /**
     * Update report status (ADMIN ONLY)
     * @throws CitizenReportNotFound if report not found
     * @throws UnauthorizedAccessException if user is not admin
     * Triggers notification when status is updated
     */
    CitizenReportDTO updateReportStatus(Long reportId, String newStatus, String userRole);
    
    /**
     * Delete a report
     * @throws CitizenReportNotFound if report not found
     */
    void deleteReport(Long reportId);
}