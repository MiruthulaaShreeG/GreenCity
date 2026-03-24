package com.cognizant.greencity.dto.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ResourceUpdateRequest {

    @Size(max = 50)
    private String type;

    @Size(max = 255)
    private String location;

    @Min(0)
    private Double capacity;

    @Size(max = 50)
    private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

