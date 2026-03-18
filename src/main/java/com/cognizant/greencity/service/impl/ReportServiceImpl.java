package com.example.sustainability.service.impl;

import com.example.sustainability.dto.ReportDTO;
import com.example.sustainability.entity.*;
import com.example.sustainability.repository.*;
import com.example.sustainability.service.ReportService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;
    private final ProjectRepository projectRepository;
    private final ComplianceRecordRepository complianceRepository;
    private final FeedbackRepository feedbackRepository;
    private final ResourceRepository resourceRepository;

    public ReportServiceImpl(
            ReportRepository reportRepository,
            UserRepository userRepository,
            AuditRepository auditRepository,
            ProjectRepository projectRepository,
            ComplianceRecordRepository complianceRepository,
            FeedbackRepository feedbackRepository,
            ResourceRepository resourceRepository) {

        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
        this.projectRepository = projectRepository;
        this.complianceRepository = complianceRepository;
        this.feedbackRepository = feedbackRepository;
        this.resourceRepository = resourceRepository;
    }

    // =======================
    // CREATE REPORT
    // =======================
    @Override
    public ReportDTO createReport(ReportDTO dto) {

        Report report = new Report();

        report.setGeneratedBy(
                userRepository.findById(dto.getGeneratedByUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        report.setAudit(
                auditRepository.findById(dto.getAuditId())
                        .orElseThrow(() -> new RuntimeException("Audit not found"))
        );

        if (dto.getProjectId() != null) {
            report.setProject(
                    projectRepository.findById(dto.getProjectId())
                            .orElseThrow(() -> new RuntimeException("Project not found"))
            );
        }

        if (dto.getComplianceId() != null) {
            report.setComplianceRecord(
                    complianceRepository.findById(dto.getComplianceId())
                            .orElseThrow(() -> new RuntimeException("Compliance record not found"))
            );
        }

        if (dto.getFeedbackId() != null) {
            report.setFeedback(
                    feedbackRepository.findById(dto.getFeedbackId())
                            .orElseThrow(() -> new RuntimeException("Feedback not found"))
            );
        }

        if (dto.getResourceId() != null) {
            report.setResource(
                    resourceRepository.findById(dto.getResourceId())
                            .orElseThrow(() -> new RuntimeException("Resource not found"))
            );
        }

        report.setScope(dto.getScope());
        report.setMetrics(dto.getMetrics());
        report.setGeneratedDate(LocalDateTime.now());

        return mapToDTO(reportRepository.save(report));
    }

    // =======================
    // READ OPERATIONS
    // =======================
    @Override
    public ReportDTO getReportById(Integer reportId) {
        return reportRepository.findById(reportId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    @Override
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByScope(String scope) {
        return reportRepository.findByScope(scope)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByProject(Integer projectId) {
        return reportRepository.findByProject_ProjectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByResource(Integer resourceId) {
        return reportRepository.findByResource_ResourceId(resourceId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByCompliance(Integer complianceId) {
        return reportRepository.findByComplianceRecord_ComplianceId(complianceId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByFeedback(Integer feedbackId) {
        return reportRepository.findByFeedback_FeedbackId(feedbackId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByUser(Integer userId) {
        return reportRepository.findByGeneratedBy_UserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // =======================
    // DELETE
    // =======================
    @Override
    public void deleteReport(Integer reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new RuntimeException("Report not found");
        }
        reportRepository.deleteById(reportId);
    }

    // =======================
    // MAPPER
    // =======================
    private ReportDTO mapToDTO(Report report) {

        ReportDTO dto = new ReportDTO();

        dto.setReportId(report.getReportId());
        dto.setGeneratedByUserId(report.getGeneratedBy().getUserId());
        dto.setAuditId(report.getAudit().getAuditId());

        dto.setProjectId(
                report.getProject() != null ? report.getProject().getProjectId() : null
        );
        dto.setComplianceId(
                report.getComplianceRecord() != null ? report.getComplianceRecord().getComplianceId() : null
        );
        dto.setFeedbackId(
                report.getFeedback() != null ? report.getFeedback().getFeedbackId() : null
        );
        dto.setResourceId(
                report.getResource() != null ? report.getResource().getResourceId() : null
        );

        dto.setScope(report.getScope());
        dto.setMetrics(report.getMetrics());
        dto.setGeneratedDate(report.getGeneratedDate());

        return dto;
    }
}
``