package com.cognizant.greencity;

import com.cognizant.greencity.dao.FeedbackRepository;
import com.cognizant.greencity.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Submit new feedback
    public Feedback submitFeedback(Feedback feedback) {
        feedback.setDate(LocalDate.now());
        if (feedback.getStatus() == null) {
            feedback.setStatus("SUBMITTED");
        }
        return feedbackRepository.save(feedback);
    }


    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // Get feedback by ID
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }
}