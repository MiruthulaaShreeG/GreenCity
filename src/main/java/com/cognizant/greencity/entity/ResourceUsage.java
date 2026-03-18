package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resource_usage")
public class ResourceUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usageid", updatable = false, nullable = false)
    private Integer usageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceid", nullable = false)
    private Resource resource;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Getters and Setters
    public Integer getUsageId() { return usageId; }
    public void setUsageId(Integer usageId) { this.usageId = usageId; }

    public Resource getResource() { return resource; }
    public void setResource(Resource resource) { this.resource = resource; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
