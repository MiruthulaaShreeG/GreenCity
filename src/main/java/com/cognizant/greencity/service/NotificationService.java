package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.Notification;
import java.util.List;

public interface NotificationService {
    /**
     * Create a new notification
     */
    Notification createNotification(Notification notification);
    
    /**
     * Get all notifications for a citizen
     */
    List<Notification> getNotificationsByCitizenId(Long citizenId);
    
    /**
     * Get unread notifications for a citizen
     */
    List<Notification> getUnreadNotifications(Long citizenId);
    
    /**
     * Mark notification as read
     */
    Notification markAsRead(Long notificationId);
    
    /**
     * Delete a notification
     */
    void deleteNotification(Long notificationId);
}
