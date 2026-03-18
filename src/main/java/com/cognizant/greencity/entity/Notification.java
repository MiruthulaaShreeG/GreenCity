package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @NotNull(message = "Citizen ID cannot be null")
    @Column(name = "citizen_id", nullable = false)
    private Long citizenId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizen_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User citizen;

    @NotBlank(message = "Notification type cannot be blank")
    @Column(name = "notification_type", length = 50, nullable = false)
    private String notificationType;  // REPORT_CREATED, STATUS_UPDATED, FEEDBACK_RECEIVED

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "related_report_id")
    private Long relatedReportId;

    @Column(name = "related_feedback_id")
    private Long relatedFeedbackId;

    @NotBlank(message = "Status cannot be blank")
    @Column(name = "status", length = 50, nullable = false)
    private String status = "UNREAD";  // UNREAD, READ

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "read_date")
    private LocalDateTime readDate;

    public Notification() {
    }

    public Notification(Long notificationId, Long citizenId, User citizen, String notificationType, String title, String message, Long relatedReportId, Long relatedFeedbackId, String status, LocalDateTime createdDate, LocalDateTime readDate) {
        this.notificationId = notificationId;
        this.citizenId = citizenId;
        this.citizen = citizen;
        this.notificationType = notificationType;
        this.title = title;
        this.message = message;
        this.relatedReportId = relatedReportId;
        this.relatedFeedbackId = relatedFeedbackId;
        this.status = status;
        this.createdDate = createdDate;
        this.readDate = readDate;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public User getCitizen() {
        return citizen;
    }

    public void setCitizen(User citizen) {
        this.citizen = citizen;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRelatedReportId() {
        return relatedReportId;
    }

    public void setRelatedReportId(Long relatedReportId) {
        this.relatedReportId = relatedReportId;
    }

    public Long getRelatedFeedbackId() {
        return relatedFeedbackId;
    }

    public void setRelatedFeedbackId(Long relatedFeedbackId) {
        this.relatedFeedbackId = relatedFeedbackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDateTime readDate) {
        this.readDate = readDate;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "UNREAD";
        }
    }
}
