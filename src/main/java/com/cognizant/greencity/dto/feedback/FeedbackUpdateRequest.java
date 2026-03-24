package com.cognizant.greencity.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognizant.greencity.entity.Feedback;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackUpdateRequest {
    private Feedback.Category category;

    @Size(max = 255)
    private String comments;

    @Size(max = 255)
    private String status;
}
