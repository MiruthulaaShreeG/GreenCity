package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.FeedbackDTO;
import com.cognizant.greencity.Enum.Category;
import java.util.List;

public interface FeedbackService {

    FeedbackDTO submitFeedback(FeedbackDTO feedbackDto);

    List<FeedbackDTO> getAllFeedback();

    FeedbackDTO getFeedbackById(Long id);

    List<FeedbackDTO> getFeedbackByCitizenId(Long citizenId);

    List<FeedbackDTO> getFeedbackByCategory(Category category);

    FeedbackDTO updateFeedbackStatus(Long feedbackId, String newStatus, String userRole);

    void deleteFeedback(Long feedbackId);
}