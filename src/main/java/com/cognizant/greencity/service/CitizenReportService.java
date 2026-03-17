package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.CitizenReportDTO;

import java.util.List;

public interface CitizenReportService {
    CitizenReportDTO fileReport(CitizenReportDTO reportDto);
    List<CitizenReportDTO> getAllReports();
    CitizenReportDTO updateReportStatus(long reportId, String newStatus);
}