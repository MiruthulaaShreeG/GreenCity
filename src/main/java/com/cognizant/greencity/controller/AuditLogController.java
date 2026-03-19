package com.cognizant.greencity.controller;
import com.cognizant.greencity.dto.AuditLogDTO;
import com.cognizant.greencity.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;
    @GetMapping("/all")
    public ResponseEntity<List<AuditLogDTO>> getAllLogs() {
        List<AuditLogDTO> logs = auditLogService.getAllAuditLogs();
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logs);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUserId(userId));
    }

    @PostMapping("/test-log")
    public ResponseEntity<String> createManualLog(@RequestParam Integer userId, @RequestParam String action) {
        return ResponseEntity.ok("Log created successfully");
    }

}
