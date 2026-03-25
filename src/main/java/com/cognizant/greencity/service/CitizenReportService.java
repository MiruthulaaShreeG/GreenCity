package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.report.CitizenReportCreateRequest;
import com.cognizant.greencity.dto.report.CitizenReportResponse;
import com.cognizant.greencity.dto.report.CitizenReportUpdateRequest;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.CitizenReportRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitizenReportService {

    private final CitizenReportRepository citizenReportRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<CitizenReportResponse> listMine(Authentication authentication) {
        Integer userId = principal(authentication).getUserId();
        return citizenReportRepository.findByCitizen_UserId(userId).stream().map(this::toResponse).toList();
    }

    public CitizenReportResponse getMine(Integer id, Authentication authentication) {
        CitizenReport report = getEntity(id);
        enforceOwner(report, authentication);
        return toResponse(report);
    }

    public CitizenReportResponse create(CitizenReportCreateRequest request, Authentication authentication) {
        User user = currentUser(authentication);

        CitizenReport report = new CitizenReport();
        report.setCitizen(user);
        report.setType(request.getType());
        report.setLocation(request.getLocation());
        report.setStatus(request.getStatus() != null ? request.getStatus() : "OPEN");

        CitizenReport saved = citizenReportRepository.save(report);
        auditLogService.record(user, "CITIZEN_REPORT_CREATE", "citizen-reports/" + saved.getReportId());
        return toResponse(saved);
    }

    public CitizenReportResponse update(Integer id, CitizenReportUpdateRequest request, Authentication authentication) {
        CitizenReport report = getEntity(id);
        enforceOwner(report, authentication);

        if (request.getType() != null) report.setType(request.getType());
        if (request.getLocation() != null) report.setLocation(request.getLocation());
        if (request.getStatus() != null) report.setStatus(request.getStatus());

        CitizenReport saved = citizenReportRepository.save(report);
        auditLogService.record(saved.getCitizen(), "CITIZEN_REPORT_UPDATE", "citizen-reports/" + id);
        return toResponse(saved);
    }

    public void delete(Integer id, Authentication authentication) {
        CitizenReport report = getEntity(id);
        enforceOwner(report, authentication);
        citizenReportRepository.delete(report);
        auditLogService.record(report.getCitizen(), "CITIZEN_REPORT_DELETE", "citizen-reports/" + id);
    }

    private CitizenReport getEntity(Integer id) {
        return citizenReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
    }

    private CitizenReportResponse toResponse(CitizenReport report) {
        CitizenReportResponse response = modelMapper.map(report, CitizenReportResponse.class);
        response.setCitizenId(report.getCitizen() != null ? report.getCitizen().getUserId() : null);
        return response;
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

