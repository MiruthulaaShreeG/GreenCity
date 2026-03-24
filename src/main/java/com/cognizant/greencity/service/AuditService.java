package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.audit.AuditCreateRequest;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.AuditRepository;
import com.cognizant.greencity.repository.ComplianceRecordRepository;
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
public class AuditService {

    private final AuditRepository auditRepository;
    private final ComplianceRecordRepository complianceRecordRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<AuditResponse> listByCompliance(Integer complianceId) {
        return auditRepository.findByComplianceRecord_ComplianceId(complianceId).stream()
                .map(this::toResponse)
                .toList();
    }

    public AuditResponse getByCompliance(Integer complianceId, Integer auditId) {
        return toResponse(getEntityByCompliance(complianceId, auditId));
    }

    public AuditResponse create(Integer complianceId, AuditCreateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);
        ComplianceRecord complianceRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("ComplianceRecord not found"));

        Audit audit = modelMapper.map(request, Audit.class);
        audit.setOfficer(officer);
        audit.setComplianceRecord(complianceRecord);
        if (audit.getDate() == null) {
            audit.setDate(LocalDateTime.now());
        }

        Audit saved = auditRepository.save(audit);
        auditLogService.record(officer, "AUDIT_CREATE", "audits/" + saved.getAuditId());
        return toResponse(saved);
    }

    public AuditResponse update(Integer complianceId, Integer auditId, AuditUpdateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);
        Audit audit = getEntityByCompliance(complianceId, auditId);

        if (request.getScope() != null) audit.setScope(request.getScope());
        if (request.getFindings() != null) audit.setFindings(request.getFindings());
        if (request.getDate() != null) audit.setDate(request.getDate());
        if (request.getStatus() != null) audit.setStatus(request.getStatus());

        Audit saved = auditRepository.save(audit);
        auditLogService.record(officer, "AUDIT_UPDATE", "audits/" + saved.getAuditId());
        return toResponse(saved);
    }

    public void delete(Integer complianceId, Integer auditId, Authentication authentication) {
        User officer = currentUser(authentication);
        Audit audit = getEntityByCompliance(complianceId, auditId);
        auditRepository.delete(audit);
        auditLogService.record(officer, "AUDIT_DELETE", "audits/" + auditId);
    }

    private Audit getEntityByCompliance(Integer complianceId, Integer auditId) {
        return auditRepository.findByAuditIdAndComplianceRecord_ComplianceId(auditId, complianceId)
                .orElseThrow(() -> new NotFoundException("Audit not found"));
    }

    private AuditResponse toResponse(Audit audit) {
        AuditResponse response = modelMapper.map(audit, AuditResponse.class);
        response.setComplianceId(audit.getComplianceRecord() != null ? audit.getComplianceRecord().getComplianceId() : null);
        response.setOfficerId(audit.getOfficer() != null ? audit.getOfficer().getUserId() : null);
        return response;
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

