package com.cognizant.greencity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_reports")
public class CitizenReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportid")
    private Integer reportId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private User citizen;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReportType type;
    @Column(name = "location")
    private String location;
    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();
    @Column(name = "status")
    private String status;
    public enum ReportType {
        POLLUTION, WASTE;
    }

    public Integer getReportID() {
        return reportId;
    }

    public void setReportID(Integer reportID) {
        this.reportId = reportID;
    }

    public User getCitizen() {
        return citizen;
    }

    public void setCitizen(User citizen) {
        this.citizen = citizen;
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

    public CitizenReport(Integer reportID, User citizen, ReportType type, String location, LocalDateTime date, String status) {
        this.reportId = reportID;
        this.citizen = citizen;
        this.type = type;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public CitizenReport(){

    }
}
