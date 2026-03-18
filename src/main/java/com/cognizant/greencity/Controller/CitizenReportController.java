package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.CitizenReportDTO;
import com.cognizant.greencity.service.CitizenReportService;
import com.cognizant.greencity.exception.CitizenReportNotFound;
import com.cognizant.greencity.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Citizen Report Operations
 * Endpoints for filing, retrieving, and managing sustainability reports
 */
@RestController
@RequestMapping("/api/citizen-reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CitizenReportController {

    @Autowired
    private CitizenReportService reportService;

    /**
     * File a new sustainability report
     * POST /api/citizen-reports
     *
     * @param reportDTO - Report details (citizenId, type, location, etc.)
     * @return Created report with status 201
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> fileReport(@Valid @RequestBody CitizenReportDTO reportDTO) {
        CitizenReportDTO created = reportService.fileReport(reportDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Report filed successfully");
        response.put("data", created);
        response.put("status", "PENDING");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all reports
     * GET /api/citizen-reports
     *
     * @return List of all reports
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReports() {
        List<CitizenReportDTO> reports = reportService.getAllReports();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reports retrieved successfully");
        response.put("count", reports.size());
        response.put("data", reports);
        return ResponseEntity.ok(response);
    }

    /**
     * Get reports by citizen ID
     * GET /api/citizen-reports/citizen/{citizenId}
     *
     * @param citizenId - ID of the citizen
     * @return List of reports for the citizen
     */
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<Map<String, Object>> getReportsByCitizenId(
            @PathVariable Long citizenId) {
        List<CitizenReportDTO> reports = reportService.getReportsByCitizenId(citizenId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Citizen reports retrieved successfully");
        response.put("citizenId", citizenId);
        response.put("count", reports.size());
        response.put("data", reports);
        return ResponseEntity.ok(response);
    }

    /**
     * Get report by report ID
     * GET /api/citizen-reports/{reportId}
     *
     * @param reportId - ID of the report
     * @return Report details
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<Map<String, Object>> getReportById(@PathVariable Long reportId) {
        CitizenReportDTO report = reportService.getReportById(reportId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Report retrieved successfully");
        response.put("data", report);
        return ResponseEntity.ok(response);
    }

    /**
     * Update report status (ADMIN ONLY)
     * PUT /api/citizen-reports/{reportId}/status
     * Only admins can update status - citizens cannot update their own reports
     *
     * @param reportId - ID of the report
     * @param statusUpdate - New status and user role
     * @return Updated report
     */
    @PutMapping("/{reportId}/status")
    public ResponseEntity<Map<String, Object>> updateReportStatus(
            @PathVariable Long reportId,
            @Valid @RequestBody StatusUpdateRequest statusUpdate) {
        CitizenReportDTO updated = reportService.updateReportStatus(
                reportId,
                statusUpdate.getNewStatus(),
                statusUpdate.getUserRole()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Report status updated successfully");
        response.put("previousStatus", statusUpdate.getPreviousStatus());
        response.put("newStatus", statusUpdate.getNewStatus());
        response.put("data", updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a report
     * DELETE /api/citizen-reports/{reportId}
     *
     * @param reportId - ID of the report to delete
     * @return Success message
     */
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Map<String, String>> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Report deleted successfully");
        response.put("reportId", reportId.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Inner class for status update request
     */
    public static class StatusUpdateRequest {
        private String newStatus;
        private String userRole;
        private String previousStatus;

        public String getNewStatus() {
            return newStatus;
        }

        public void setNewStatus(String newStatus) {
            this.newStatus = newStatus;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getPreviousStatus() {
            return previousStatus;
        }

        public void setPreviousStatus(String previousStatus) {
            this.previousStatus = previousStatus;
        }
    }
}

