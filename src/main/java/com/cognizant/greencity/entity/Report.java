package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "reports",
        indexes = {
                @Index(name = "idx_reports_scope", columnList = "scope"),
                @Index(name = "idx_reports_generated_date", columnList = "generated_date")
        }
)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId; // ReportID

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, length = 32)
    private Scope scope; // Scope [RESOURCE / PROJECT / FEEDBACK]

    /**
     * JSON string holding metrics for the report.
     * Example:
     * {
     *   "totalResources": 128,
     *   "utilizationRate": 0.82,
     *   "projectsActive": 14,
     *   "complianceIssues": 3
     * }
     */
    @Lob
    @Column(name = "metrics", nullable = false, columnDefinition = "TEXT")
    private String metrics; // Metrics as JSON

    @Column(name = "generated_date", nullable = false)
    private Instant generatedDate; // GeneratedDate

    public Report() { }

    public Report(Scope scope, String metrics, Instant generatedDate) {
        this.scope = scope;
        this.metrics = metrics;
        this.generatedDate = generatedDate;
    }

    public Integer getReportId() { return reportId; }
    public void setReportId(Integer reportId) { this.reportId = reportId; }

    public Scope getScope() { return scope; }
    public void setScope(Scope scope) { this.scope = scope; }

    public String getMetrics() { return metrics; }
    public void setMetrics(String metrics) { this.metrics = metrics; }

    public Instant getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(Instant generatedDate) { this.generatedDate = generatedDate; }

    public enum Scope {
        RESOURCE,
        PROJECT,
        FEEDBACK
    }
}