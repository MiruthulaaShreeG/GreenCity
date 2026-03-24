package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.compliance.ComplianceRecordCreateRequest;

import com.cognizant.greencity.dto.compliance.ComplianceRecordUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.BadRequestException;
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
public class ComplianceRecordService {

    private final ComplianceRecordRepository complianceRecordRepository;
    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public ComplianceRecordService(
            ComplianceRecordRepository complianceRecordRepository,
            AuditRepository auditRepository,
            UserRepository userRepository,
            AuditLogService auditLogService
    ) {
        this.complianceRecordRepository = complianceRecordRepository;
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<ComplianceRecord> list() {
        return complianceRecordRepository.findAll();
    }

    public ComplianceRecord get(Integer complianceId) {
        return complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("ComplianceRecord not found"));
    }

    public ComplianceRecord create(ComplianceRecordCreateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);

        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(request.getEntityId());
        record.setEntityType(request.getEntityType());
        record.setResult(request.getResult());
        record.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        record.setNotes(request.getNotes());

        ComplianceRecord saved = complianceRecordRepository.save(record);
        auditLogService.record(officer, "COMPLIANCE_CREATE", "compliance_records/" + saved.getComplianceId());
        return saved;
    }

    public ComplianceRecord update(Integer complianceId, ComplianceRecordUpdateRequest request, Authentication authentication) {
        User officer = currentUser(authentication);
        ComplianceRecord record = get(complianceId);

        if (request.getResult() != null) record.setResult(request.getResult());
        if (request.getDate() != null) record.setDate(request.getDate());
        if (request.getNotes() != null) record.setNotes(request.getNotes());

        ComplianceRecord saved = complianceRecordRepository.save(record);
        auditLogService.record(officer, "COMPLIANCE_UPDATE", "compliance_records/" + complianceId);
        return saved;
    }

    public void delete(Integer complianceId, Authentication authentication) {
        User officer = currentUser(authentication);
        ComplianceRecord record = get(complianceId);

        List<Audit> audits = auditRepository.findByComplianceRecord_ComplianceId(complianceId);
        if (audits != null && !audits.isEmpty()) {
            throw new BadRequestException("Cannot delete compliance record with existing audits");
        }

        complianceRecordRepository.delete(record);
        auditLogService.record(officer, "COMPLIANCE_DELETE", "compliance_records/" + complianceId);
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

