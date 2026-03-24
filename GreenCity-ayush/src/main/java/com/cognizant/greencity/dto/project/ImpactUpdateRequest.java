package com.cognizant.greencity.dto.project;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ImpactUpdateRequest {

    private LocalDateTime date;

    @Size(max = 1000000)
    private String metricsJson;

    @Size(max = 255)
    private String status;

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

