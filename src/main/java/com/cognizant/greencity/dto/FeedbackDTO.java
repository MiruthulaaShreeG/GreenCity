package com.cognizant.greencity.dto;

import com.cognizant.greencity.Enum.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
public class FeedbackDTO {
    private Long feedbackId;

    @NotNull(message = "Citizen ID cannot be null")
    @Positive(message = "Citizen ID must be positive")
    private Long citizenId;

    @NotNull(message = "Category cannot be null")
    private Category category;

    @NotBlank(message = "Comments cannot be blank")
    @Size(min = 10, max = 1000, message = "Comments must be between 10 and 1000 characters")
    private String comments;

    private String status;

    private LocalDateTime date;

    public FeedbackDTO() {
    }

    public FeedbackDTO(Long feedbackId, Long citizenId, Category category, String comments, String status, LocalDateTime date) {
        this.feedbackId = feedbackId;
        this.citizenId = citizenId;
        this.category = category;
        this.comments = comments;
        this.status = status;
        this.date = date;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
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
