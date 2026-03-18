package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_report")
public class CitizenReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportID;

    @NotNull(message = "Citizen ID cannot be null")
    @Column(name = "citizen_id", nullable = false)
    private Long citizenID;

    // Foreign Key reference to User (no lazy loading to avoid LazyInitializationException)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizen_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User citizen;

    @NotNull(message = "Report type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private ReportType type;

    @NotBlank(message = "Location cannot be blank")
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime date;

    @NotBlank(message = "Status cannot be blank")
    @Column(name = "status", length = 50, nullable = false)
    private String status = "PENDING";  // Default status


    public enum ReportType {
        POLLUTION, WASTE
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Long getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(Long citizenID) {
        this.citizenID = citizenID;
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

    public CitizenReport() {
        this.date = LocalDateTime.now();
    }

    public CitizenReport(Long reportID, Long citizenID, ReportType type, String location, LocalDateTime date, String status) {
        this.reportID = reportID;
        this.citizenID = citizenID;
        this.type = type;
        this.location = location;
        this.date = date != null ? date : LocalDateTime.now();
        this.status = status != null ? status : "PENDING";
    }
}
