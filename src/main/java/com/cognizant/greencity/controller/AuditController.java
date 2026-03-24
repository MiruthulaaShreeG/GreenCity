package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditCreateRequest;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
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
        return auditService.listByCompliance(complianceId);
    }

    @GetMapping("/{auditId}")
    public AuditResponse get(@PathVariable Integer complianceId, @PathVariable Integer auditId) {
        return auditService.getByCompliance(complianceId, auditId);
    }

    @PostMapping
    public AuditResponse create(@PathVariable Integer complianceId,
                                @Valid @RequestBody AuditCreateRequest request,
                                Authentication authentication) {
        return auditService.create(complianceId, request, authentication);
    }

    @PutMapping("/{auditId}")
    public AuditResponse update(@PathVariable Integer complianceId,
                                @PathVariable Integer auditId,
                                @Valid @RequestBody AuditUpdateRequest request,
                                Authentication authentication) {
        return auditService.update(complianceId, auditId, request, authentication);
    }

    @DeleteMapping("/{auditId}")
    public void delete(@PathVariable Integer complianceId,
                       @PathVariable Integer auditId,
                       Authentication authentication) {
        auditService.delete(complianceId, auditId, authentication);
    }
}

