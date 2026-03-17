package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "Resource")
public class Resource {

    @Id
    @GeneratedValue
    @Column(name = "ResourceID", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID resourceId;

    @Column(name = "Type", nullable = false, length = 50)
    private String type; // Energy / Water / Waste

    @Column(name = "Location", nullable = false, length = 255)
    private String location;

    @Column(name = "Capacity", nullable = false)
    private Double capacity;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    // One resource can have multiple usage records
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceUsage> usages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Getters and Setters
    public UUID getResourceId() { return resourceId; }
    public void setResourceId(UUID resourceId) { this.resourceId = resourceId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<ResourceUsage> getUsages() { return usages; }
    public void setUsages(List<ResourceUsage> usages) { this.usages = usages; }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

