package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void record(User user, String action, String resource) {
        if (user == null) {
            return;
        }
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResources(resource);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}

