package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditLogCreateRequest;
import com.cognizant.greencity.dto.audit.AuditLogResponse;
import com.cognizant.greencity.service.AuditLogCrudService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogCrudService auditLogService;

    public AuditLogController(AuditLogCrudService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogResponse> list() {
        return auditLogService.list();
    }

    @GetMapping("/{id}")
    public AuditLogResponse get(@PathVariable Integer id) {
        return auditLogService.get(id);
    }

    @PostMapping
    public AuditLogResponse create(@Valid @RequestBody AuditLogCreateRequest request) {
        return auditLogService.create(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        auditLogService.delete(id);
    }
}

