package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.Feedback;
import com.cognizant.greencity.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    /**
     * Find all feedback submitted by a specific citizen
     */
    @Query("SELECT f FROM Feedback f WHERE f.citizenID = :citizenId ORDER BY f.date DESC")
    List<Feedback> findByCitizenID(@Param("citizenId") Long citizenId);
    
    /**
     * Find feedback by citizen and category
     */
    @Query("SELECT f FROM Feedback f WHERE f.citizenID = :citizenId AND f.category = :category")
    List<Feedback> findByCitizenIDAndCategory(@Param("citizenId") Long citizenId, @Param("category") Category category);
    
    /**
     * Find feedback by category
     */
    @Query("SELECT f FROM Feedback f WHERE f.category = :category ORDER BY f.date DESC")
    List<Feedback> findByCategory(@Param("category") Category category);
    
    /**
     * Find feedback by status
     */
    @Query("SELECT f FROM Feedback f WHERE f.status = :status ORDER BY f.date DESC")
    List<Feedback> findByStatus(@Param("status") String status);
}
