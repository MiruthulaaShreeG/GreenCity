package com.cognizant.greencity.dto.audit;

import java.time.LocalDateTime;

public class AuditLogResponse {
    private Integer auditId;
    private Integer userId;
    private String action;
    private String resources;
    private LocalDateTime timestamp;

    public AuditLogResponse() {}

    public AuditLogResponse(Integer auditId, Integer userId, String action, String resources, LocalDateTime timestamp) {
        this.auditId = auditId;
        this.userId = userId;
        this.action = action;
        this.resources = resources;
        this.timestamp = timestamp;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

