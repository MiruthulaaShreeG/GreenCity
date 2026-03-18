package com.cognizant.greencity.entity;
import com.cognizant.greencity.Enum.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackID;

    @NotNull(message = "Citizen ID cannot be null")
    @Column(name = "citizen_id", nullable = false)
    private Long citizenID;

    // Foreign Key reference to User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizen_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User citizen;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20, nullable = false)
    private Category category;

    @NotBlank(message = "Comments cannot be blank")
    @Size(min = 10, max = 1000, message = "Comments must be between 10 and 1000 characters")
    @Column(name = "comments", columnDefinition = "TEXT", nullable = false)
    private String comments;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime date;

    @NotBlank(message = "Status cannot be blank")
    @Column(name = "status", length = 50, nullable = false)
    private String status = "SUBMITTED";  // Default status


    public Long getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Long feedbackID) {
        this.feedbackID = feedbackID;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    //Constructor//
    public Feedback() {
        this.date = LocalDateTime.now();
    }

    public Feedback(Long feedbackID, Long citizenID, Category category, String comments, LocalDateTime date, String status) {
        this.feedbackID = feedbackID;
        this.citizenID = citizenID;
        this.category = category;
        this.comments = comments;
        this.date = date != null ? date : LocalDateTime.now();
        this.status = status != null ? status : "SUBMITTED";
    }
}

