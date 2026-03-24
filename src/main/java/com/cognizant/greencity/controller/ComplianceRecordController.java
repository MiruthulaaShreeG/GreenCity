package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.compliance.ComplianceRecordCreateRequest;
import com.cognizant.greencity.dto.compliance.ComplianceRecordResponse;
import com.cognizant.greencity.dto.compliance.ComplianceRecordUpdateRequest;
import com.cognizant.greencity.service.ComplianceRecordService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records")
public class ComplianceRecordController {

    private final ComplianceRecordService complianceRecordService;

    public ComplianceRecordController(ComplianceRecordService complianceRecordService) {
        this.complianceRecordService = complianceRecordService;
    }

    @GetMapping
    public List<ComplianceRecordResponse> list() {
        return complianceRecordService.list();
    }

    @GetMapping("/{complianceId}")
    public ComplianceRecordResponse get(@PathVariable Integer complianceId) {
        return complianceRecordService.get(complianceId);
    }

    @PostMapping
    public ComplianceRecordResponse create(@Valid @RequestBody ComplianceRecordCreateRequest request,
                                           Authentication authentication) {
        return complianceRecordService.create(request, authentication);
    }

    @PutMapping("/{complianceId}")
    public ComplianceRecordResponse update(@PathVariable Integer complianceId,
                                           @Valid @RequestBody ComplianceRecordUpdateRequest request,
                                           Authentication authentication) {
        return complianceRecordService.update(complianceId, request, authentication);
    }

    @DeleteMapping("/{complianceId}")
    public void delete(@PathVariable Integer complianceId, Authentication authentication) {
        complianceRecordService.delete(complianceId, authentication);
    }
}

