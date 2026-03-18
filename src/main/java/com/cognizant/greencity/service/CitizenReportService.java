package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.CitizenReportDTO;
import java.util.List;

public interface CitizenReportService {

    CitizenReportDTO fileReport(CitizenReportDTO reportDto);

    List<CitizenReportDTO> getAllReports();

    List<CitizenReportDTO> getReportsByCitizenId(Long citizenId);

    CitizenReportDTO getReportById(Long reportId);

    CitizenReportDTO updateReportStatus(Long reportId, String newStatus, String userRole);

    void deleteReport(Long reportId);
}