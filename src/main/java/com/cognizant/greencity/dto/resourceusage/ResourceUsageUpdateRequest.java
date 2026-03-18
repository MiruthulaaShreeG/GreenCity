package com.cognizant.greencity.dto.resourceusage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ResourceUsageUpdateRequest {

    @Min(0)
    private Double quantity;

    @Size(max = 50)
    private String status;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

