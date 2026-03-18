package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.FeedbackDTO;
import com.cognizant.greencity.service.FeedbackService;
import com.cognizant.greencity.Enum.Category;
import com.cognizant.greencity.exception.FeedbackNotFound;
import com.cognizant.greencity.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * REST API Controller for Feedback Operations
 * Endpoints for submitting, retrieving, and managing citizen feedback
 */
@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * Submit new feedback
     * POST /api/feedback
     *
     * @param feedbackDTO - Feedback details (citizenId, category, comments, etc.)
     * @return Created feedback with status 201
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitFeedback(
            @Valid @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO created = feedbackService.submitFeedback(feedbackDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Feedback submitted successfully");
        response.put("data", created);
        response.put("status", "SUBMITTED");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all feedback
     * GET /api/feedback
     *
     * @return List of all feedback entries
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllFeedback() {
        List<FeedbackDTO> feedbackList = feedbackService.getAllFeedback();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "All feedback retrieved successfully");
        response.put("count", feedbackList.size());
        response.put("data", feedbackList);
        return ResponseEntity.ok(response);
    }

    /**
     * Get feedback by ID
     * GET /api/feedback/{feedbackId}
     *
     * @param feedbackId - ID of the feedback
     * @return Feedback details
     */
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Map<String, Object>> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackDTO feedback = feedbackService.getFeedbackById(feedbackId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Feedback retrieved successfully");
        response.put("data", feedback);
        return ResponseEntity.ok(response);
    }

    /**
     * Get feedback by citizen ID
     * GET /api/feedback/citizen/{citizenId}
     *
     * @param citizenId - ID of the citizen
     * @return List of feedback from the citizen
     */
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<Map<String, Object>> getFeedbackByCitizenId(
            @PathVariable Long citizenId) {
        List<FeedbackDTO> feedbackList = feedbackService.getFeedbackByCitizenId(citizenId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Citizen feedback retrieved successfully");
        response.put("citizenId", citizenId);
        response.put("count", feedbackList.size());
        response.put("data", feedbackList);
        return ResponseEntity.ok(response);
    }

    /**
     * Get feedback by category
     * GET /api/feedback/category/{category}
     *
     * @param category - Category filter (Waste, Energy, Water)
     * @return List of feedback in the category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getFeedbackByCategory(
            @PathVariable String category) {
        try {
            // Allow case-insensitive values like "water" or "WATER"
            Category categoryEnum = Category.valueOf(category.toUpperCase(Locale.ROOT));
            List<FeedbackDTO> feedbackList = feedbackService.getFeedbackByCategory(categoryEnum);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Feedback by category retrieved successfully");
            response.put("category", category);
            response.put("count", feedbackList.size());
            response.put("data", feedbackList);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category. Must be one of: Waste, Energy, Water");
        }
    }

    /**
     * Update feedback status (ADMIN ONLY)
     * PUT /api/feedback/{feedbackId}/status
     * Only admins can update status - citizens cannot update their own feedback
     *
     * @param feedbackId - ID of the feedback
     * @param statusUpdate - New status and user role
     * @return Updated feedback
     */
    @PutMapping("/{feedbackId}/status")
    public ResponseEntity<Map<String, Object>> updateFeedbackStatus(
            @PathVariable Long feedbackId,
            @Valid @RequestBody StatusUpdateRequest statusUpdate) {
        FeedbackDTO updated = feedbackService.updateFeedbackStatus(
                feedbackId,
                statusUpdate.getNewStatus(),
                statusUpdate.getUserRole()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Feedback status updated successfully");
        response.put("previousStatus", statusUpdate.getPreviousStatus());
        response.put("newStatus", statusUpdate.getNewStatus());
        response.put("data", updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete feedback
     * DELETE /api/feedback/{feedbackId}
     *
     * @param feedbackId - ID of the feedback to delete
     * @return Success message
     */
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Map<String, String>> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Feedback deleted successfully");
        response.put("feedbackId", feedbackId.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Inner class for status update request
     */
    public static class StatusUpdateRequest {
        private String newStatus;
        private String userRole;
        private String previousStatus;

        public StatusUpdateRequest() {
        }

        public StatusUpdateRequest(String newStatus, String userRole, String previousStatus) {
            this.newStatus = newStatus;
            this.userRole = userRole;
            this.previousStatus = previousStatus;
        }

        public String getNewStatus() {
            return newStatus;
        }

        public void setNewStatus(String newStatus) {
            this.newStatus = newStatus;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getPreviousStatus() {
            return previousStatus;
        }

        public void setPreviousStatus(String previousStatus) {
            this.previousStatus = previousStatus;
        }
    }
}
