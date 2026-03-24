package com.cognizant.greencity.dto.feedback;

import com.cognizant.greencity.entity.Feedback;

import java.time.LocalDate;

public class FeedbackResponse {
    private Integer feedbackId;
    private Integer citizenId;
    private Feedback.Category category;
    private String comments;
    private LocalDate date;
    private String status;

    public FeedbackResponse() {}

    public FeedbackResponse(Integer feedbackId, Integer citizenId, Feedback.Category category, String comments, LocalDate date, String status) {
        this.feedbackId = feedbackId;
        this.citizenId = citizenId;
        this.category = category;
        this.comments = comments;
        this.date = date;
        this.status = status;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Integer citizenId) {
        this.citizenId = citizenId;
    }

    public Feedback.Category getCategory() {
        return category;
    }

    public void setCategory(Feedback.Category category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

