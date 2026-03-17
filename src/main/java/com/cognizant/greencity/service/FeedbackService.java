package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO submitFeedback(FeedbackDTO feedbackDto);
    List<FeedbackDTO> getAllFeedback();
    FeedbackDTO getFeedbackById(int id);
}