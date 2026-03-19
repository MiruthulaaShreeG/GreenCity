package com.cognizant.greencity.service;
import com.cognizant.greencity.dto.AuditLogDTO;
import com.cognizant.greencity.entity.User;
import java.util.List;

public interface AuditLogService {
    void logAction(User user, String action, String resource);
    List<AuditLogDTO> getAllAuditLogs();
    List<AuditLogDTO> getLogsByUserId(Integer userId);
}
