package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Integer auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", nullable = false)
    private User officer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compliance_id", nullable = false)
    private ComplianceRecord complianceRecord;

    @Column(name = "scope")
    private String scope; // e.g., "Resource", "Project", "Follow-up"

    @Column(name = "findings", columnDefinition = "TINYTEXT")
    private String findings;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status; // e.g., "Open", "In Review", "Closed", "Approved"

    public Audit() { }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public User getOfficer() {
        return officer;
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }

    public ComplianceRecord getComplianceRecord() {
        return complianceRecord;
    }

    public void setComplianceRecord(ComplianceRecord complianceRecord) {
        this.complianceRecord = complianceRecord;
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