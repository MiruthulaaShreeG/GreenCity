package com.example.sustainability.service;

import com.example.sustainability.dto.ReportDTO;

import java.util.List;

public interface ReportService {

    ReportDTO createReport(ReportDTO reportDTO);
    ReportDTO getReportById(Integer reportId);
    List<ReportDTO> getAllReports();
    List<ReportDTO> getReportsByScope(String scope);
    List<ReportDTO> getReportsByProject(Integer projectId);
    List<ReportDTO> getReportsByResource(Integer resourceId);
    List<ReportDTO> getReportsByCompliance(Integer complianceId);
    List<ReportDTO> getReportsByFeedback(Integer feedbackId);
    List<ReportDTO> getReportsByUser(Integer userId);

    void deleteReport(Integer reportId);
}