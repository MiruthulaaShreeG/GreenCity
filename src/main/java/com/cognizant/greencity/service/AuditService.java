package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.audit.AuditCreateRequest;

import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.AuditRepository;
import com.cognizant.greencity.repository.ComplianceRecordRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final ComplianceRecordRepository complianceRecordRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public AuditService(
            AuditRepository auditRepository,
            ComplianceRecordRepository complianceRecordRepository,
            UserRepository userRepository,
            AuditLogService auditLogService
    ) {
        this.auditRepository = auditRepository;
        this.complianceRecordRepository = complianceRecordRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<Audit> listByCompliance(Integer complianceId) {
        return auditRepository.findByComplianceRecord_ComplianceId(complianceId);
    }

    public Audit getByCompliance(Integer complianceId, Integer auditId) {
        return auditRepository.findByAuditIdAndComplianceRecord_ComplianceId(auditId, complianceId)
                .orElseThrow(() -> new NotFoundException("Audit not found"));
    }

    public Audit create(Integer complianceId, AuditCreateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);
        ComplianceRecord complianceRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("ComplianceRecord not found"));

        Audit audit = new Audit();
        audit.setOfficer(officer);
        audit.setComplianceRecord(complianceRecord);
        audit.setScope(request.getScope());
        audit.setFindings(request.getFindings());
        audit.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        audit.setStatus(request.getStatus());

        Audit saved = auditRepository.save(audit);
        auditLogService.record(officer, "AUDIT_CREATE", "audits/" + saved.getAuditId());
        return saved;
    }

    public Audit update(Integer complianceId, Integer auditId, AuditUpdateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);
        Audit audit = getByCompliance(complianceId, auditId);

        if (request.getScope() != null) audit.setScope(request.getScope());
        if (request.getFindings() != null) audit.setFindings(request.getFindings());
        if (request.getDate() != null) audit.setDate(request.getDate());
        if (request.getStatus() != null) audit.setStatus(request.getStatus());

        Audit saved = auditRepository.save(audit);
        auditLogService.record(officer, "AUDIT_UPDATE", "audits/" + saved.getAuditId());
        return saved;
    }

    public void delete(Integer complianceId, Integer auditId, Authentication authentication) {
        User officer = currentUser(authentication);
        Audit audit = getByCompliance(complianceId, auditId);
        auditRepository.delete(audit);
        auditLogService.record(officer, "AUDIT_DELETE", "audits/" + auditId);
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

