package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    /**
     * Find all notifications for a citizen ordered by date descending
     */
    @Query("SELECT n FROM Notification n WHERE n.citizenId = :citizenId ORDER BY n.createdDate DESC")
    List<Notification> findByCitizenIdOrderByCreatedDateDesc(@Param("citizenId") Long citizenId);
    
    /**
     * Find notifications by citizen and status
     */
    @Query("SELECT n FROM Notification n WHERE n.citizenId = :citizenId AND n.status = :status ORDER BY n.createdDate DESC")
    List<Notification> findByCitizenIdAndStatusOrderByCreatedDateDesc(
            @Param("citizenId") Long citizenId, 
            @Param("status") String status
    );
}
