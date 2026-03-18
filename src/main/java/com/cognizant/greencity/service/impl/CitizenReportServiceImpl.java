package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.CitizenReportRepository;
import com.cognizant.greencity.dto.CitizenReportDTO;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.entity.Notification;
import com.cognizant.greencity.service.CitizenReportService;
import com.cognizant.greencity.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenReportServiceImpl implements CitizenReportService {

    @Autowired
    private CitizenReportRepository reportRepository;

    @Autowired
    private NotificationServiceImpl notificationService;

    /**
     * File a new report (Create)
     * Validates input and creates notification
     */
    @Override
    @Transactional
    public CitizenReportDTO fileReport(CitizenReportDTO dto) {
        try {
            // Validate required fields
            if (dto.getCitizenId() == null || dto.getCitizenId() <= 0) {
                throw new BadRequestException("Citizen ID must be a positive number");
            }
            if (dto.getType() == null || dto.getType().trim().isEmpty()) {
                throw new BadRequestException("Report type is required");
            }
            if (dto.getLocation() == null || dto.getLocation().trim().isEmpty()) {
                throw new BadRequestException("Location is required and cannot be empty");
            }

            // Validate type is correct
            try {
                CitizenReport.ReportType.valueOf(dto.getType().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid report type. Must be POLLUTION or WASTE");
            }

            CitizenReport entity = new CitizenReport();
            entity.setCitizenID(dto.getCitizenId());
            entity.setType(CitizenReport.ReportType.valueOf(dto.getType().toUpperCase()));
            entity.setLocation(dto.getLocation());
            entity.setDate(LocalDateTime.now());
            entity.setStatus("PENDING");  // Default status

            CitizenReport saved = reportRepository.save(entity);

            // Create notification
            Notification notification = new Notification();
            notification.setCitizenId(dto.getCitizenId());
            notification.setNotificationType("REPORT_CREATED");
            notification.setTitle("Report Submitted Successfully");
            notification.setMessage("Your " + dto.getType() + " report has been submitted and is pending review.");
            notification.setRelatedReportId(saved.getReportID());
            notification.setStatus("UNREAD");
            notificationService.createNotification(notification);

            return mapToDTO(saved);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error filing report: " + e.getMessage(), e);
        }
    }

    /**
     * Get all reports (Read)
     */
    @Override
    public List<CitizenReportDTO> getAllReports() {
        try {
            return reportRepository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving all reports: " + e.getMessage(), e);
        }
    }

    /**
     * Get all reports by citizen ID
     */
    @Override
    public List<CitizenReportDTO> getReportsByCitizenId(Long citizenId) {
        try {
            if (citizenId == null || citizenId <= 0) {
                throw new BadRequestException("Citizen ID must be a positive number");
            }

            List<CitizenReport> reports = reportRepository.findByCitizenID(citizenId);
            if (reports.isEmpty()) {
                throw new CitizenReportNotFound("No reports found for citizen with ID: " + citizenId);
            }

            return reports.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (CitizenReportNotFound e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving reports for citizen: " + e.getMessage(), e);
        }
    }

    /**
     * Get report by ID
     */
    @Override
    public CitizenReportDTO getReportById(Long reportId) {
        try {
            if (reportId == null || reportId <= 0) {
                throw new BadRequestException("Report ID must be a positive number");
            }

            return reportRepository.findById(reportId)
                    .map(this::mapToDTO)
                    .orElseThrow(() -> new CitizenReportNotFound("Report not found with ID: " + reportId));
        } catch (CitizenReportNotFound e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving report: " + e.getMessage(), e);
        }
    }

    /**
     * Update report status (ADMIN ONLY)
     * Only admins can update status, citizens cannot
     */
    @Override
    @Transactional
    public CitizenReportDTO updateReportStatus(Long reportId, String newStatus, String userRole) {
        try {
            // Only ADMIN can update status
            if (userRole == null || !userRole.equals("ADMIN")) {
                throw new UnauthorizedAccessException("Only admins can update report status");
            }

            if (reportId == null || reportId <= 0) {
                throw new BadRequestException("Report ID must be a positive number");
            }

            if (newStatus == null || newStatus.trim().isEmpty()) {
                throw new BadRequestException("Status cannot be empty");
            }

            CitizenReport report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new CitizenReportNotFound("Report not found with ID: " + reportId));

            String oldStatus = report.getStatus();
            report.setStatus(newStatus);
            CitizenReport updated = reportRepository.save(report);

            // Create notification about status change
            Notification notification = new Notification();
            notification.setCitizenId(report.getCitizenID());
            notification.setNotificationType("STATUS_UPDATED");
            notification.setTitle("Report Status Updated");
            notification.setMessage("Your report status has been updated from " + oldStatus + " to " + newStatus);
            notification.setRelatedReportId(reportId);
            notification.setStatus("UNREAD");
            notificationService.createNotification(notification);

            return mapToDTO(updated);
        } catch (CitizenReportNotFound | BadRequestException | UnauthorizedAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error updating report status: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a report
     */
    @Override
    @Transactional
    public void deleteReport(Long reportId) {
        try {
            if (reportId == null || reportId <= 0) {
                throw new BadRequestException("Report ID must be a positive number");
            }

            if (!reportRepository.existsById(reportId)) {
                throw new CitizenReportNotFound("Report not found with ID: " + reportId);
            }

            reportRepository.deleteById(reportId);
        } catch (CitizenReportNotFound | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error deleting report: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method for mapping entity to DTO
     */
    private CitizenReportDTO mapToDTO(CitizenReport entity) {
        CitizenReportDTO dto = new CitizenReportDTO();
        dto.setReportId(entity.getReportID());
        dto.setCitizenId(entity.getCitizenID());
        dto.setType(entity.getType().toString());
        dto.setLocation(entity.getLocation());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());
        return dto;
    }
}