package com.cognizant.greencity.dto;

import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;

import java.time.LocalDateTime;

public class AuditDTO {
    private Integer auditId;
    private Integer officerId;
    private Integer complianceRecordId;
    private String scope;
    private String findings;
    private LocalDateTime date;
    private String status;
    public  AuditDTO() {};
    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Integer officerId) {
        this.officerId = officerId;
    }

    public Integer getComplianceRecordId() {
        return complianceRecordId;
    }

    public void setComplianceRecordId(Integer complianceRecordId) {
        this.complianceRecordId = complianceRecordId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
