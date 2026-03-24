package com.cognizant.greencity.dto.resourceusage;

import java.time.LocalDateTime;

public class ResourceUsageResponse {

    private Integer usageId;
    private Integer resourceId;
    private Double quantity;
    private LocalDateTime date;
    private String status;

    public ResourceUsageResponse() {}

    public ResourceUsageResponse(Integer usageId, Integer resourceId, Double quantity, LocalDateTime date, String status) {
        this.usageId = usageId;
        this.resourceId = resourceId;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public Integer getUsageId() {
        return usageId;
    }

    public void setUsageId(Integer usageId) {
        this.usageId = usageId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

