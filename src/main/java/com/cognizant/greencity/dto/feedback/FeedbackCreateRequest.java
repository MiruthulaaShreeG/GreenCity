package com.cognizant.greencity.dto.feedback;

import com.cognizant.greencity.entity.Feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FeedbackCreateRequest {
    @NotNull
    private Feedback.Category category;

    @NotBlank
    @Size(max = 255)
    private String comments;

    @Size(max = 255)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

