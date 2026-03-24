package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.audit.AuditLogCreateRequest;
import com.cognizant.greencity.dto.audit.AuditLogResponse;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.AuditLogRepository;
import com.cognizant.greencity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogCrudService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<AuditLogResponse> list() {
        return auditLogRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AuditLogResponse get(Integer id) {
        return toResponse(getEntity(id));
    }

    public AuditLogResponse create(AuditLogCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(request.getAction());
        log.setResources(request.getResources());
        return toResponse(auditLogRepository.save(log));
    }

    public void delete(Integer id) {
        AuditLog log = getEntity(id);
        auditLogRepository.delete(log);
    }

    private AuditLog getEntity(Integer id) {
        return auditLogRepository.findById(id).orElseThrow(() -> new NotFoundException("AuditLog not found"));
    }

    private AuditLogResponse toResponse(AuditLog log) {
        AuditLogResponse response = modelMapper.map(log, AuditLogResponse.class);
        response.setUserId(log.getUser() != null ? log.getUser().getUserId() : null);
        return response;
    }
}

