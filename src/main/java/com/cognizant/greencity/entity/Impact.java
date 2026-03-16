package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "impacts")
public class Impact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impact_id")
    private Integer impactId;

    // Many impacts belong to one project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "metrics_json", columnDefinition = "TEXT")
    private String metricsJson;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status;

    public Impact() {
    }

    public Integer getImpactId() {
        return impactId;
    }

    public void setImpactId(Integer impactId) {
        this.impactId = impactId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getMetricsJson() {
        return metricsJson;
    }

    public void setMetricsJson(String metricsJson) {
        this.metricsJson = metricsJson;
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