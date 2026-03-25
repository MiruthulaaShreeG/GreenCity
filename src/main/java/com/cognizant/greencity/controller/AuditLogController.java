package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.audit.AuditLogCreateRequest;
import com.cognizant.greencity.dto.audit.AuditLogResponse;
import com.cognizant.greencity.service.AuditLogCrudService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogController.class);
    private final AuditLogCrudService auditLogService;

    public AuditLogController(AuditLogCrudService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogResponse> list() {
        logger.info("Received request to list audit logs");
        List<AuditLogResponse> response = auditLogService.list();
        logger.info("Successfully fetched audit logs");
        return response;
    }

    @GetMapping("/{id}")
    public AuditLogResponse get(@PathVariable Integer id) {
        logger.info("Received request to get audit log with id: {}", id);
        AuditLogResponse response = auditLogService.get(id);
        logger.info("Successfully fetched audit log with id: {}", id);
        return response;
    }

    @PostMapping
    public AuditLogResponse create(@Valid @RequestBody AuditLogCreateRequest request) {
        logger.info("Received request to create an audit log");
        AuditLogResponse response = auditLogService.create(request);
        logger.info("Successfully created audit log");
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        logger.info("Received request to delete audit log with id: {}", id);
        auditLogService.delete(id);
        logger.info("Successfully deleted audit log with id: {}", id);
    }
}