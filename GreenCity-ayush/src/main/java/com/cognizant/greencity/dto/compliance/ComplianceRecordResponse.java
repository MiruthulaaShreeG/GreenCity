package com.cognizant.greencity.dto.compliance;

import java.time.LocalDateTime;

public class ComplianceRecordResponse {
    private Integer complianceId;
    private Integer entityId;
    private String entityType;
    private String result;
    private LocalDateTime date;
    private String notes;

    public ComplianceRecordResponse() {}

    public ComplianceRecordResponse(Integer complianceId, Integer entityId, String entityType, String result, LocalDateTime date, String notes) {
        this.complianceId = complianceId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.result = result;
        this.date = date;
        this.notes = notes;
    }

    public Integer getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(Integer complianceId) {
        this.complianceId = complianceId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

