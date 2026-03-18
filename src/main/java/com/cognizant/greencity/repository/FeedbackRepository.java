package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByCitizen_UserId(Integer userId);
}

