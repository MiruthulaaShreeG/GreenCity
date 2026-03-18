package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.FeedbackRepository;
import com.cognizant.greencity.dao.UserRepository;
import com.cognizant.greencity.dto.FeedbackDTO;
import com.cognizant.greencity.entity.Feedback;
import com.cognizant.greencity.entity.Notification;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.service.FeedbackService;
import com.cognizant.greencity.Enum.Category;
import com.cognizant.greencity.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public FeedbackDTO submitFeedback(FeedbackDTO dto) {
        try {
            // Validate required fields
            if (dto.getCitizenId() == null || dto.getCitizenId() <= 0) {
                throw new BadRequestException("Citizen ID must be a positive number");
            }
            if (dto.getCategory() == null) {
                throw new BadRequestException("Category is required and must be one of: Waste, Energy, Water");
            }
            if (dto.getComments() == null || dto.getComments().trim().isEmpty()) {
                throw new BadRequestException("Comments cannot be empty");
            }
            if (dto.getComments().length() < 10) {
                throw new BadRequestException("Comments must be at least 10 characters long");
            }
            if (dto.getComments().length() > 1000) {
                throw new BadRequestException("Comments cannot exceed 1000 characters");
            }

            Feedback entity = new Feedback();
            User citizen = userRepository.findById(dto.getCitizenId())
                    .orElseThrow(() -> new BadRequestException("Citizen not found with ID: " + dto.getCitizenId()));
            entity.setCitizen(citizen);
            entity.setCategory(dto.getCategory());
            entity.setComments(dto.getComments());
            entity.setDate(LocalDateTime.now());
            entity.setStatus("SUBMITTED");  // Default status

            Feedback saved = feedbackRepository.save(entity);

            // Create notification
            Notification notification = new Notification();
            notification.setCitizenId(dto.getCitizenId());
            notification.setNotificationType("FEEDBACK_RECEIVED");
            notification.setTitle("Feedback Submitted Successfully");
            notification.setMessage("Your feedback on " + dto.getCategory() + " has been received. Thank you!");
            notification.setRelatedFeedbackId(saved.getFeedbackID());
            notification.setStatus("UNREAD");
            notificationService.createNotification(notification);

            return mapToDTO(saved);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error submitting feedback: " + e.getMessage(), e);
        }
    }


    @Override
    public List<FeedbackDTO> getAllFeedback() {
        try {
            return feedbackRepository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving feedback: " + e.getMessage(), e);
        }
    }

    @Override
    public FeedbackDTO getFeedbackById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new BadRequestException("Feedback ID must be a positive number");
            }

            return feedbackRepository.findById(id)
                    .map(this::mapToDTO)
                    .orElseThrow(() -> new FeedbackNotFound("Feedback not found with ID: " + id));
        } catch (FeedbackNotFound e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving feedback: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FeedbackDTO> getFeedbackByCitizenId(Long citizenId) {
        try {
            if (citizenId == null || citizenId <= 0) {
                throw new BadRequestException("Citizen ID must be a positive number");
            }

            List<Feedback> feedbackList = feedbackRepository.findByCitizenID(citizenId);
            if (feedbackList.isEmpty()) {
                throw new FeedbackNotFound("No feedback found for citizen with ID: " + citizenId);
            }

            return feedbackList.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (FeedbackNotFound e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving feedback for citizen: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FeedbackDTO> getFeedbackByCategory(Category category) {
        try {
            if (category == null) {
                throw new BadRequestException("Category is required");
            }

            List<Feedback> feedbackList = feedbackRepository.findByCategory(category);
            if (feedbackList.isEmpty()) {
                throw new FeedbackNotFound("No feedback found for category: " + category);
            }

            return feedbackList.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (FeedbackNotFound e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving feedback by category: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public FeedbackDTO updateFeedbackStatus(Long feedbackId, String newStatus, String userRole) {
        try {
            // Only ADMIN can update status
            if (userRole == null || !userRole.equals("ADMIN")) {
                throw new UnauthorizedAccessException("Only admins can update feedback status");
            }

            if (feedbackId == null || feedbackId <= 0) {
                throw new BadRequestException("Feedback ID must be a positive number");
            }

            if (newStatus == null || newStatus.trim().isEmpty()) {
                throw new BadRequestException("Status cannot be empty");
            }

            Feedback feedback = feedbackRepository.findById(feedbackId)
                    .orElseThrow(() -> new FeedbackNotFound("Feedback not found with ID: " + feedbackId));

            feedback.setStatus(newStatus);
            Feedback updated = feedbackRepository.save(feedback);

            return mapToDTO(updated);
        } catch (FeedbackNotFound | BadRequestException | UnauthorizedAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error updating feedback status: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteFeedback(Long feedbackId) {
        try {
            if (feedbackId == null || feedbackId <= 0) {
                throw new BadRequestException("Feedback ID must be a positive number");
            }

            if (!feedbackRepository.existsById(feedbackId)) {
                throw new FeedbackNotFound("Feedback not found with ID: " + feedbackId);
            }

            feedbackRepository.deleteById(feedbackId);
        } catch (FeedbackNotFound | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServiceError("Error deleting feedback: " + e.getMessage(), e);
        }
    }


    private FeedbackDTO mapToDTO(Feedback entity) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setFeedbackId(entity.getFeedbackID());
        dto.setCitizenId(entity.getCitizen().getUserId());
        dto.setCategory(entity.getCategory());
        dto.setComments(entity.getComments());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());
        return dto;
    }
}