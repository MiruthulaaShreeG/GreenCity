package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditLogCreateRequest;
import com.cognizant.greencity.dto.audit.AuditLogResponse;
import com.cognizant.greencity.service.AuditLogCrudService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {


    private final AuditLogCrudService auditLogService;

    public AuditLogController(AuditLogCrudService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogResponse> list() {
        log.info("Received request to list audit logs");
        List<AuditLogResponse> response = auditLogService.list();
        log.info("Successfully fetched audit logs");
        return response;
    }

    @GetMapping("/{id}")
    public AuditLogResponse get(@PathVariable Integer id) {
        log.info("Received request to get audit log with id: {}", id);
        AuditLogResponse response = auditLogService.get(id);
        log.info("Successfully fetched audit log with id: {}", id);
        return response;
    }

    @PostMapping
    public AuditLogResponse create(@Valid @RequestBody AuditLogCreateRequest request) {
        log.info("Received request to create an audit log");
        AuditLogResponse response = auditLogService.create(request);
        log.info("Successfully created audit log");
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Received request to delete audit log with id: {}", id);
        auditLogService.delete(id);
        log.info("Successfully deleted audit log with id: {}", id);
    }
}