package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.compliance.ComplianceRecordCreateRequest;
import com.cognizant.greencity.dto.compliance.ComplianceRecordResponse;
import com.cognizant.greencity.dto.compliance.ComplianceRecordUpdateRequest;
import com.cognizant.greencity.entity.ComplianceRecord;
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
        return complianceRecordService.list().stream().map(ComplianceRecordController::toResponse).toList();
    }

    @GetMapping("/{complianceId}")
    public ComplianceRecordResponse get(@PathVariable Integer complianceId) {
        return toResponse(complianceRecordService.get(complianceId));
    }

    @PostMapping
    public ComplianceRecordResponse create(@Valid @RequestBody ComplianceRecordCreateRequest request,
                                              Authentication authentication) {
        return toResponse(complianceRecordService.create(request, authentication));
    }

    @PutMapping("/{complianceId}")
    public ComplianceRecordResponse update(@PathVariable Integer complianceId,
                                              @Valid @RequestBody ComplianceRecordUpdateRequest request,
                                              Authentication authentication) {
        return toResponse(complianceRecordService.update(complianceId, request, authentication));
    }

    @DeleteMapping("/{complianceId}")
    public void delete(@PathVariable Integer complianceId, Authentication authentication) {
        complianceRecordService.delete(complianceId, authentication);
    }

    private static ComplianceRecordResponse toResponse(ComplianceRecord record) {
        return new ComplianceRecordResponse(
                record.getComplianceId(),
                record.getEntityId(),
                record.getEntityType(),
                record.getResult(),
                record.getDate(),
                record.getNotes()
        );
    }
}

