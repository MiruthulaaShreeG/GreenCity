package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.audit.AuditLogCreateRequest;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.AuditLogRepository;
import com.cognizant.greencity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogCrudService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogCrudService(AuditLogRepository auditLogRepository, UserRepository userRepository) {
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    public List<AuditLog> list() {
        return auditLogRepository.findAll();
    }

    public AuditLog get(Integer id) {
        return auditLogRepository.findById(id).orElseThrow(() -> new NotFoundException("AuditLog not found"));
    }

    public AuditLog create(AuditLogCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(request.getAction());
        log.setResources(request.getResources());
        return auditLogRepository.save(log);
    }

    public void delete(Integer id) {
        AuditLog log = get(id);
        auditLogRepository.delete(log);
    }
}

