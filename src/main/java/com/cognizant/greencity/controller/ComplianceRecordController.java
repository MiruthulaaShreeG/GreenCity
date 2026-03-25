package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.compliance.ComplianceRecordCreateRequest;
import com.cognizant.greencity.dto.compliance.ComplianceRecordResponse;
import com.cognizant.greencity.dto.compliance.ComplianceRecordUpdateRequest;
import com.cognizant.greencity.service.ComplianceRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records")
public class ComplianceRecordController {

    private static final Logger logger = LoggerFactory.getLogger(ComplianceRecordController.class);
    private final ComplianceRecordService complianceRecordService;

    public ComplianceRecordController(ComplianceRecordService complianceRecordService) {
        this.complianceRecordService = complianceRecordService;
    }

    @GetMapping
    public List<ComplianceRecordResponse> list() {
        logger.info("Received request to list compliance records");
        List<ComplianceRecordResponse> response = complianceRecordService.list();
        logger.info("Successfully fetched compliance records");
        return response;
    }

    @GetMapping("/{complianceId}")
    public ComplianceRecordResponse get(@PathVariable Integer complianceId) {
        logger.info("Received request to get compliance record id: {}", complianceId);
        ComplianceRecordResponse response = complianceRecordService.get(complianceId);
        logger.info("Successfully fetched compliance record id: {}", complianceId);
        return response;
    }

    @PostMapping
    public ComplianceRecordResponse create(@Valid @RequestBody ComplianceRecordCreateRequest request,
                                           Authentication authentication) {
        logger.info("Received request to create a compliance record");
        ComplianceRecordResponse response = complianceRecordService.create(request, authentication);
        logger.info("Successfully created compliance record");
        return response;
    }

    @PutMapping("/{complianceId}")
    public ComplianceRecordResponse update(@PathVariable Integer complianceId,
                                           @Valid @RequestBody ComplianceRecordUpdateRequest request,
                                           Authentication authentication) {
        logger.info("Received request to update compliance record id: {}", complianceId);
        ComplianceRecordResponse response = complianceRecordService.update(complianceId, request, authentication);
        logger.info("Successfully updated compliance record id: {}", complianceId);
        return response;
    }

    @DeleteMapping("/{complianceId}")
    public void delete(@PathVariable Integer complianceId, Authentication authentication) {
        logger.info("Received request to delete compliance record id: {}", complianceId);
        complianceRecordService.delete(complianceId, authentication);
        logger.info("Successfully deleted compliance record id: {}", complianceId);
    }
}