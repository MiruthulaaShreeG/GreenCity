package com.cognizant.greencity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private int feedbackId;
    private int citizenId;
    private String category;
    private String comments;
    private String status;
    private LocalDateTime date;
}