package com.cognizant.greencity.dto.audit;

import java.time.LocalDateTime;

public class AuditResponse {
    private Integer auditId;
    private Integer complianceId;
    private Integer officerId;
    private String scope;
    private String findings;
    private LocalDateTime date;
    private String status;

    public AuditResponse() {}

    public AuditResponse(Integer auditId, Integer complianceId, Integer officerId, String scope, String findings, LocalDateTime date, String status) {
        this.auditId = auditId;
        this.complianceId = complianceId;
        this.officerId = officerId;
        this.scope = scope;
        this.findings = findings;
        this.date = date;
        this.status = status;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(Integer complianceId) {
        this.complianceId = complianceId;
    }

    public Integer getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Integer officerId) {
        this.officerId = officerId;
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

