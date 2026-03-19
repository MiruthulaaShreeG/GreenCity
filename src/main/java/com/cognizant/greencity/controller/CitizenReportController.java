package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.CitizenReportDTO;
import com.cognizant.greencity.service.CitizenReportService;
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> fileReport(@Valid @RequestBody CitizenReportDTO reportDTO) {
        CitizenReportDTO created = reportService.fileReport(reportDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Report filed successfully");
        response.put("data", created);
        response.put("status", "PENDING");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReports() {
        List<CitizenReportDTO> reports = reportService.getAllReports();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reports retrieved successfully");
        response.put("count", reports.size());
        response.put("data", reports);
        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/{reportId}")
    public ResponseEntity<Map<String, Object>> getReportById(@PathVariable Long reportId) {
        CitizenReportDTO report = reportService.getReportById(reportId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Report retrieved successfully");
        response.put("data", report);
        return ResponseEntity.ok(response);
    }

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

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Map<String, String>> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Report deleted successfully");
        response.put("reportId", reportId.toString());
        return ResponseEntity.ok(response);
    }

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

