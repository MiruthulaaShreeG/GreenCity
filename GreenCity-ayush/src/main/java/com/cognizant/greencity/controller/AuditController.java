package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditCreateRequest;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records/{complianceId}/audits")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public List<AuditResponse> list(@PathVariable Integer complianceId) {
        return auditService.listByCompliance(complianceId).stream()
                .map(AuditController::toResponse)
                .toList();
    }

    @GetMapping("/{auditId}")
    public AuditResponse get(@PathVariable Integer complianceId, @PathVariable Integer auditId) {
        return toResponse(auditService.getByCompliance(complianceId, auditId));
    }

    @PostMapping
    public AuditResponse create(@PathVariable Integer complianceId,
                                  @Valid @RequestBody AuditCreateRequest request,
                                  Authentication authentication) {
        return toResponse(auditService.create(complianceId, request, authentication));
    }

    @PutMapping("/{auditId}")
    public AuditResponse update(@PathVariable Integer complianceId,
                                  @PathVariable Integer auditId,
                                  @Valid @RequestBody AuditUpdateRequest request,
                                  Authentication authentication) {
        return toResponse(auditService.update(complianceId, auditId, request, authentication));
    }

    @DeleteMapping("/{auditId}")
    public void delete(@PathVariable Integer complianceId,
                         @PathVariable Integer auditId,
                         Authentication authentication) {
        auditService.delete(complianceId, auditId, authentication);
    }

    private static AuditResponse toResponse(Audit audit) {
        return new AuditResponse(
                audit.getAuditId(),
                audit.getComplianceRecord() != null ? audit.getComplianceRecord().getComplianceId() : null,
                audit.getOfficer() != null ? audit.getOfficer().getUserId() : null,
                audit.getScope(),
                audit.getFindings(),
                audit.getDate(),
                audit.getStatus()
        );
    }
}

