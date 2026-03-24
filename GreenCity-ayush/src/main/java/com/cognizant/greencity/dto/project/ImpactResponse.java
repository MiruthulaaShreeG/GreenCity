package com.cognizant.greencity.dto.project;

import java.time.LocalDateTime;

public class ImpactResponse {
    private Integer impactId;
    private Integer projectId;
    private LocalDateTime date;
    private String metricsJson;
    private String status;

    public ImpactResponse() {}

    public ImpactResponse(Integer impactId, Integer projectId, LocalDateTime date, String metricsJson, String status) {
        this.impactId = impactId;
        this.projectId = projectId;
        this.date = date;
        this.metricsJson = metricsJson;
        this.status = status;
    }

    public Integer getImpactId() {
        return impactId;
    }

    public void setImpactId(Integer impactId) {
        this.impactId = impactId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMetricsJson() {
        return metricsJson;
    }

    public void setMetricsJson(String metricsJson) {
        this.metricsJson = metricsJson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

