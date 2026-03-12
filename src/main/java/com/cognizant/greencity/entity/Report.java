package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.Instant;

/*
 * Reporting & Analytics Module
 *
 * Relationships Reference (as provided):
 *
 * // User Relationships
 * Ref: audit_logs.user_id         > users.user_id
 * Ref: feedbacks.citizen_id       > users.user_id
 * Ref: citizen_reports.citizen_id > users.user_id
 * Ref: notifications.user_id      > users.user_id
 * Ref: audits.officer_id          > users.user_id
 * Ref: reports.generated_by       > users.user_id
 *
 * // Resource Relationships
 * Ref: resource_usages.resource_id > resources.resource_id
 *
 * // Project Relationships
 * Ref: milestones.project_id       > projects.project_id
 * Ref: impacts.project_id          > projects.project_id
 *
 * // Compliance & Notification Polymorphic Relationships
 * Ref: compliance_records.entity_id > projects.project_id
 * Ref: compliance_records.entity_id > resources.resource_id
 * Ref: notifications.entity_id      > projects.project_id
 * Ref: notifications.entity_id      > resources.resource_id
 *
 * // Notification to Report Relationship
 * Ref: notifications.report_id     > reports.report_id
 *
 * // Compliance & Audit Relationships
 * Ref: audits.compliance_id        > compliance_records.compliance_id
 * Ref: reports.audit_id            > audits.audit_id
 */

@Entity
@Table(name = "reports")
public class Report {

    public enum Scope {
        RESOURCE, PROJECT, FEEDBACK, COMPLIANCE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private Scope scope;

    // Store metrics JSON/text; adjust columnDefinition in DDL if needed
    @Column(name = "metrics", nullable = false, columnDefinition = "TEXT")
    private String metrics;

    @Column(name = "generated_date", nullable = false)
    private Instant generatedDate;

    // Ref: reports.generated_by > users.user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;

    // Ref: reports.audit_id > audits.audit_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    // Polymorphic linkage used by compliance_records / notifications
    // Ref: entity_id -> projects.project_id OR resources.resource_id
    @Column(name = "entity_id")
    private Long entityId;

    public Report() {

    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public Instant getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Instant generatedDate) {
        this.generatedDate = generatedDate;
    }

    public User getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}