package com.cognizant.greencity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class CitizenReportDTO {
    private Long reportId;

    @NotNull(message = "Citizen ID cannot be null")
    @Positive(message = "Citizen ID must be positive")
    private Long citizenId;

    @NotBlank(message = "Report type cannot be blank")
    @Pattern(regexp = "POLLUTION|WASTE", message = "Type must be either POLLUTION or WASTE")
    private String type;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    private String status;

    private LocalDateTime date;

    public CitizenReportDTO() {
    }

    public CitizenReportDTO(Long reportId, Long citizenId, String type, String location, String status, LocalDateTime date) {
        this.reportId = reportId;
        this.citizenId = citizenId;
        this.type = type;
        this.location = location;
        this.status = status;
        this.date = date;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
