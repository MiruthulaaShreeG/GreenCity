package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditCreateRequest;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
import com.cognizant.greencity.service.AuditService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records/{complianceId}/audits")
public class AuditController {

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public List<AuditResponse> list(@PathVariable Integer complianceId) {
        logger.info("Received request to list audits for complianceId: {}", complianceId);
        List<AuditResponse> response = auditService.listByCompliance(complianceId);
        logger.info("Successfully fetched audits for complianceId: {}", complianceId);
        return response;
    }

    @GetMapping("/{auditId}")
    public AuditResponse get(@PathVariable Integer complianceId, @PathVariable Integer auditId) {
        logger.info("Received request to get auditId: {} for complianceId: {}", auditId, complianceId);
        AuditResponse response = auditService.getByCompliance(complianceId, auditId);
        logger.info("Successfully fetched auditId: {} for complianceId: {}", auditId, complianceId);
        return response;
    }

    @PostMapping
    public AuditResponse create(@PathVariable Integer complianceId,
                                @Valid @RequestBody AuditCreateRequest request,
                                Authentication authentication) {
        logger.info("Received request to create audit for complianceId: {}", complianceId);
        AuditResponse response = auditService.create(complianceId, request, authentication);
        logger.info("Successfully created audit for complianceId: {}", complianceId);
        return response;
    }

    @PutMapping("/{auditId}")
    public AuditResponse update(@PathVariable Integer complianceId,
                                @PathVariable Integer auditId,
                                @Valid @RequestBody AuditUpdateRequest request,
                                Authentication authentication) {
        logger.info("Received request to update auditId: {} for complianceId: {}", auditId, complianceId);
        AuditResponse response = auditService.update(complianceId, auditId, request, authentication);
        logger.info("Successfully updated auditId: {} for complianceId: {}", auditId, complianceId);
        return response;
    }

    @DeleteMapping("/{auditId}")
    public void delete(@PathVariable Integer complianceId,
                       @PathVariable Integer auditId,
                       Authentication authentication) {
        logger.info("Received request to delete auditId: {} for complianceId: {}", auditId, complianceId);
        auditService.delete(complianceId, auditId, authentication);
        logger.info("Successfully deleted auditId: {} for complianceId: {}", auditId, complianceId);
    }
}