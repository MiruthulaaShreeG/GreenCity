package com.cognizant.greencity.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognizant.greencity.entity.Feedback;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Integer feedbackId;
    private Integer citizenId;
    private Feedback.Category category;
    private String comments;
    private LocalDate date;
    private String status;
}
