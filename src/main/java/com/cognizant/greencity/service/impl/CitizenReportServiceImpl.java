package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.CitizenReportRepository;
import com.cognizant.greencity.dto.CitizenReportDTO;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.service.CitizenReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenReportServiceImpl implements CitizenReportService {

    @Autowired
    private CitizenReportRepository reportRepository;

    // 1. File a new report (Create)
    @Override
    public CitizenReportDTO fileReport(CitizenReportDTO dto) {
        CitizenReport entity = new CitizenReport();
        entity.setCitizenID((long) dto.getCitizenId());
        // Assuming your Entity uses an Enum for Type
        entity.setType(CitizenReport.ReportType.valueOf(dto.getType().toUpperCase()));
        entity.setLocation(dto.getLocation());
        entity.setDate(LocalDateTime.now());
        entity.setStatus("PENDING_REVIEW");

        CitizenReport saved = reportRepository.save(entity);
        return mapToDTO(saved);
    }

    // 2. Get all reports (Read)
    @Override
    public List<CitizenReportDTO> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 3. Update report status (Update)
    @Override
    public CitizenReportDTO updateReportStatus(long reportId, String newStatus) {
        return reportRepository.findById(reportId).map(report -> {
            report.setStatus(newStatus.toUpperCase());
            CitizenReport updated = reportRepository.save(report);
            return mapToDTO(updated);
        }).orElse(null);
        // Note: In a real project, consider throwing a ResourceNotFoundException here
    }

    // Helper method for mapping
    private CitizenReportDTO mapToDTO(CitizenReport entity) {
        CitizenReportDTO dto = new CitizenReportDTO();
        dto.setReportId(entity.getReportID());
        dto.setCitizenId(Math.toIntExact(entity.getCitizenID()));
        // Ensure the DTO type is converted to String if the Entity uses Enum
        dto.setType(entity.getType().toString());
        dto.setLocation(entity.getLocation());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());
        return dto;
    }
}