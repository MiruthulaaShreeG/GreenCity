package com.cognizant.greencity.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognizant.greencity.entity.Feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCreateRequest {
    @NotNull
    private Feedback.Category category;

    @NotBlank
    @Size(max = 255)
    private String comments;

    @NotBlank
    @Size(max = 255)
    private String status;
}
