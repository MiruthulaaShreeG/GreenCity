package com.cognizant.greencity.dto.report;

import com.cognizant.greencity.entity.CitizenReport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CitizenReportCreateRequest {
    @NotNull
    private CitizenReport.ReportType type;

    @NotBlank
    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String status;

    public CitizenReport.ReportType getType() {
        return type;
    }

    public void setType(CitizenReport.ReportType type) {
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
}

