package com.cognizant.greencity.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public class ResourceUsageDTO {
    private UUID usageId;
    private UUID resourceId;
    private Double quantity;
    private LocalDateTime date;
    private String status;

    // Getters and Setters

    public UUID getUsageId() {
        return usageId;
    }

    public void setUsageId(UUID usageId) {
        this.usageId = usageId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
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

    @Override
    public String toString() {
        return "ResourceUsageDTO{" +
                "usageId=" + usageId +
                ", resourceId=" + resourceId +
                ", quantity=" + quantity +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
