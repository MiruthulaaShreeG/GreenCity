package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.FeedbackDTO;
import com.cognizant.greencity.Enum.Category;
import java.util.List;

public interface FeedbackService {
    /**
     * Submit new feedback
     * Default status: SUBMITTED
     */
    FeedbackDTO submitFeedback(FeedbackDTO feedbackDto);
    
    /**
     * Retrieve all feedback entries
     */
    List<FeedbackDTO> getAllFeedback();
    
    /**
     * Find specific feedback by ID
     * @throws FeedbackNotFound if not found
     */
    FeedbackDTO getFeedbackById(Long id);
    
    /**
     * Get all feedback submitted by a citizen
     * @throws FeedbackNotFound if no feedback found
     */
    List<FeedbackDTO> getFeedbackByCitizenId(Long citizenId);
    
    /**
     * Get feedback by category
     */
    List<FeedbackDTO> getFeedbackByCategory(Category category);
    
    /**
     * Update feedback status (ADMIN ONLY)
     * @throws FeedbackNotFound if not found
     */
    FeedbackDTO updateFeedbackStatus(Long feedbackId, String newStatus, String userRole);
    
    /**
     * Delete feedback
     * @throws FeedbackNotFound if not found
     */
    void deleteFeedback(Long feedbackId);
}