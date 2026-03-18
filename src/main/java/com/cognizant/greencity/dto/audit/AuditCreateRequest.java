package com.cognizant.greencity.dto.audit;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AuditCreateRequest {
    @Size(max = 255)
    private String scope;

    private LocalDateTime date;

    @Size(max = 255)
    private String status;

    private String findings;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }
}

