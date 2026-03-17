package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.FeedbackRepository;
import com.cognizant.greencity.dto.FeedbackDTO;
import com.cognizant.greencity.entity.Feedback;
import com.cognizant.greencity.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // 1. Submit new feedback
    @Override
    public FeedbackDTO submitFeedback(FeedbackDTO dto) {
        Feedback entity = new Feedback();
        entity.setCitizenID((int) dto.getCitizenId());
        // Standardizing category to uppercase for consistency
        entity.setCategory(Feedback.Category.valueOf(dto.getCategory().toUpperCase()));
        entity.setComments(dto.getComments());
        entity.setDate(LocalDate.from(LocalDateTime.now()));

        // Setting default status if not provided
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "SUBMITTED");

        Feedback saved = feedbackRepository.save(entity);
        return mapToDTO(saved);
    }

    // 2. Retrieve all feedback entries
    @Override
    public List<FeedbackDTO> getAllFeedback() {
        return feedbackRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 3. Find specific feedback by ID
    @Override
    public FeedbackDTO getFeedbackById(int id) {
        return feedbackRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    // Helper method to convert Entity to DTO
    private FeedbackDTO mapToDTO(Feedback entity) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setFeedbackId(Math.toIntExact(entity.getFeedbackID()));
        dto.setCitizenId(Math.toIntExact(entity.getCitizenID()));
        dto.setCategory(entity.getCategory());
        dto.setComments(entity.getComments());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate().atStartOfDay());
        return dto;
    }
}