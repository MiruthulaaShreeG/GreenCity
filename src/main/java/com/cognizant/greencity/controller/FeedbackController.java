package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.feedback.FeedbackCreateRequest;
import com.cognizant.greencity.dto.feedback.FeedbackResponse;
import com.cognizant.greencity.dto.feedback.FeedbackUpdateRequest;
import com.cognizant.greencity.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public List<FeedbackResponse> listMine(Authentication authentication) {
        return feedbackService.listMine(authentication);
    }

    @GetMapping("/{id}")
    public FeedbackResponse getMine(@PathVariable Integer id, Authentication authentication) {
        return feedbackService.getMine(id, authentication);
    }

    @PostMapping
    public FeedbackResponse create(@Valid @RequestBody FeedbackCreateRequest request, Authentication authentication) {
        return feedbackService.create(request, authentication);
    }

    @PutMapping("/{id}")
    public FeedbackResponse update(@PathVariable Integer id, @Valid @RequestBody FeedbackUpdateRequest request, Authentication authentication) {
        return feedbackService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        feedbackService.delete(id, authentication);
    }
}

