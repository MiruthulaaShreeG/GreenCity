package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.report.CitizenReportCreateRequest;
import com.cognizant.greencity.dto.report.CitizenReportUpdateRequest;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.CitizenReportRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitizenReportService {

    private final CitizenReportRepository citizenReportRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public CitizenReportService(CitizenReportRepository citizenReportRepository, UserRepository userRepository, AuditLogService auditLogService) {
        this.citizenReportRepository = citizenReportRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<CitizenReport> listMine(Authentication authentication) {
        Integer userId = principal(authentication).getUserId();
        return citizenReportRepository.findByCitizen_UserId(userId);
    }

    public CitizenReport getMine(Integer id, Authentication authentication) {
        CitizenReport report = citizenReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        enforceOwner(report, authentication);
        return report;
    }

    public CitizenReport create(CitizenReportCreateRequest request, Authentication authentication) {
        User user = currentUser(authentication);

        CitizenReport report = new CitizenReport();
        report.setCitizen(user);
        report.setType(request.getType());
        report.setLocation(request.getLocation());
        report.setStatus(request.getStatus() != null ? request.getStatus() : "OPEN");
        report.setDate(LocalDateTime.now());

        CitizenReport saved = citizenReportRepository.save(report);
        auditLogService.record(user, "CITIZEN_REPORT_CREATE", "citizen-reports/" + saved.getReportID());
        return saved;
    }

    public CitizenReport update(Integer id, CitizenReportUpdateRequest request, Authentication authentication) {
        CitizenReport report = getMine(id, authentication);

        if (request.getType() != null) report.setType(request.getType());
        if (request.getLocation() != null) report.setLocation(request.getLocation());
        if (request.getStatus() != null) report.setStatus(request.getStatus());

        CitizenReport saved = citizenReportRepository.save(report);
        auditLogService.record(saved.getCitizen(), "CITIZEN_REPORT_UPDATE", "citizen-reports/" + id);
        return saved;
    }

    public void delete(Integer id, Authentication authentication) {
        CitizenReport report = getMine(id, authentication);
        citizenReportRepository.delete(report);
        auditLogService.record(report.getCitizen(), "CITIZEN_REPORT_DELETE", "citizen-reports/" + id);
    }

    private UserPrincipal principal(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }

    private User currentUser(Authentication authentication) {
        Integer userId = principal(authentication).getUserId();
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void enforceOwner(CitizenReport report, Authentication authentication) {
        Integer userId = principal(authentication).getUserId();
        if (report.getCitizen() == null || report.getCitizen().getUserId() == null || !userId.equals(report.getCitizen().getUserId())) {
            throw new UnauthorizedException("Not allowed");
        }
    }
}

