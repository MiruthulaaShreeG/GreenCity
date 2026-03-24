package com.cognizant.greencity.dto;

import java.util.UUID;
import java.time.LocalDate;

public class FundUtilizationDTO {
    private UUID fundId;
    private Double allocatedAmount;
    private Double usedAmount;
    private Double remainingAmount;
    private String purpose;
    private LocalDate date;
    private String status;

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
