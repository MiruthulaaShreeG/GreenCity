package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "FundUtilization")
public class FundUtilization {

    @Id
    @GeneratedValue
    @Column(name = "FundID", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID fundId;

    @Column(name = "AllocatedAmount", nullable = false)
    private Double allocatedAmount;

    @Column(name = "UsedAmount", nullable = false)
    private Double usedAmount;

    @Column(name = "RemainingAmount", nullable = false)
    private Double remainingAmount;

    @Column(name = "Purpose", nullable = false, length = 255)
    private String purpose; // Specific purpose of usage

    @Column(name = "Date", nullable = false)
    private LocalDate date; // Daily update

    @Column(name = "Status", nullable = false, length = 50)
    private String status; // e.g. ACTIVE, CLOSED

    public UUID getFundId() {
        return fundId;
    }

    public void setFundId(UUID fundId) {
        this.fundId = fundId;
    }

    public Double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(Double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public Double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
