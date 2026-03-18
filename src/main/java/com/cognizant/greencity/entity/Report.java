package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.Instant;

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
    @Column(name = "metrics", nullable = false, columnDefinition = "TEXT")
    private String metrics;
    @Column(name = "generated_date", nullable = false)
    private Instant generatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;


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

//    public Audit getAudit() {
//        return audit;
//    }
//
//    public void setAudit(Audit audit) {
//        this.audit = audit;
//    }

}
