package com.cognizant.greencity.dto.resource;

public class ResourceResponse {

    private Integer resourceId;
    private Integer projectId;
    private String type;
    private String location;
    private Double capacity;
    private String status;

    public ResourceResponse() {}

    public ResourceResponse(Integer resourceId, Integer projectId, String type, String location, Double capacity, String status) {
        this.resourceId = resourceId;
        this.projectId = projectId;
        this.type = type;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

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

