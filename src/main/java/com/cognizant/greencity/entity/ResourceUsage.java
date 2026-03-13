package com.cognizant.greencity.entity;
import jakarta.persistence.*;
        import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "ResourceUsage")
public class ResourceUsage {

    @Id
    @GeneratedValue
    @Column(name = "UsageID", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID usageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ResourceID", nullable = false)
    private Resource resource;

    @Column(name = "Quantity", nullable = false)
    private Double quantity;

    @Column(name = "Date", nullable = false)
    private LocalDateTime date;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    // Getters and Setters
    public UUID getUsageId() { return usageId; }
    public void setUsageId(UUID usageId) { this.usageId = usageId; }

    public Resource getResource() { return resource; }
    public void setResource(Resource resource) { this.resource = resource; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
