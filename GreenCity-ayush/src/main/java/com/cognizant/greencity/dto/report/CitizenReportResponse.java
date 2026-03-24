package com.cognizant.greencity.dto.report;

import com.cognizant.greencity.entity.CitizenReport;

import java.time.LocalDateTime;

public class CitizenReportResponse {
    private Integer reportId;
    private Integer citizenId;
    private CitizenReport.ReportType type;
    private String location;
    private LocalDateTime date;
    private String status;

    public CitizenReportResponse() {}

    public CitizenReportResponse(Integer reportId, Integer citizenId, CitizenReport.ReportType type, String location, LocalDateTime date, String status) {
        this.reportId = reportId;
        this.citizenId = citizenId;
        this.type = type;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Integer citizenId) {
        this.citizenId = citizenId;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

