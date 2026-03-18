package com.cognizant.greencity.service.impl;
import com.cognizant.greencity.dao.AuditLogRepository;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.dto.AuditLogDTO;
import com.cognizant.greencity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.cognizant.greencity.service.AuditLogService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
   private AuditLogRepository auditLogRepository;
    @Override
    public void logAction(User user, String action, String resource) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResources(resource);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        List<AuditLog> logs = auditLogRepository.findAll();
        List<AuditLogDTO> dtos = new ArrayList<>();

        for (AuditLog log : logs) {
            dtos.add(mapToDTO(log));
        }
        return dtos;
    }
    private AuditLogDTO mapToDTO(AuditLog auditLog) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setAuditId(auditLog.getAuditId());
        dto.setAction(auditLog.getAction());
        dto.setResources(auditLog.getResources());

        if (auditLog.getUser() != null) {
            dto.setUserName(auditLog.getUser().getName());
            dto.setUserRole(auditLog.getUser().getRole());
        }


        if (auditLog.getTimestamp() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dto.setTimestamp(auditLog.getTimestamp().format(formatter));
        }

        return dto;
    }
    @Override
    public List<AuditLogDTO> getLogsByUserId(Integer userId) {
        List<AuditLog> logs = auditLogRepository.findByUser_UserId(userId);
        List<AuditLogDTO> dtos = new ArrayList<>();
        for (AuditLog log : logs) {
            dtos.add(mapToDTO(log));
        }
        return dtos;
    }
}
