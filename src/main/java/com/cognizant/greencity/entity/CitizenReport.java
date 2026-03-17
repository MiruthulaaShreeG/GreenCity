package com.cognizant.greencity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_reports")
public class CitizenReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportID;
    @ManyToOne(fetch = FetchType.LAZY)
    private Long citizenID;
    @Enumerated(EnumType.STRING)
    private ReportType type;
    private String location;
    private LocalDateTime date = LocalDateTime.now();
    private String status;
    public enum ReportType {
        POLLUTION, WASTE;
    }

    public long getReportID() {
        return reportID;
    }

    public void setReportID(long reportID) {
        this.reportID = reportID;
    }

    public Long getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(Long citizenID) {
        this.citizenID = citizenID;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
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

    public CitizenReport(long reportID, Long citizenID, ReportType type, String location, LocalDateTime date, String status) {
        this.reportID = reportID;
        this.citizenID = citizenID;
        this.type = type;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public CitizenReport(){

    }
}
