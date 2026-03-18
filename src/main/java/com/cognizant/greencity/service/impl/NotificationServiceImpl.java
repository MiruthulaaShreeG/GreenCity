package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.NotificationRepository;
import com.cognizant.greencity.entity.Notification;
import com.cognizant.greencity.service.NotificationService;
import com.cognizant.greencity.exception.InternalServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Create a new notification
     */
    @Override
    @Transactional
    public Notification createNotification(Notification notification) {
        try {
            if (notification.getCreatedDate() == null) {
                notification.setCreatedDate(LocalDateTime.now());
            }
            if (notification.getStatus() == null) {
                notification.setStatus("UNREAD");
            }
            return notificationRepository.save(notification);
        } catch (Exception e) {
            throw new InternalServiceError("Error creating notification: " + e.getMessage(), e);
        }
    }

    /**
     * Get all notifications for a citizen
     */
    @Override
    public List<Notification> getNotificationsByCitizenId(Long citizenId) {
        try {
            return notificationRepository.findByCitizenIdOrderByCreatedDateDesc(citizenId);
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving notifications: " + e.getMessage(), e);
        }
    }

    /**
     * Get unread notifications for a citizen
     */
    @Override
    public List<Notification> getUnreadNotifications(Long citizenId) {
        try {
            return notificationRepository.findByCitizenIdAndStatusOrderByCreatedDateDesc(citizenId, "UNREAD");
        } catch (Exception e) {
            throw new InternalServiceError("Error retrieving unread notifications: " + e.getMessage(), e);
        }
    }

    /**
     * Mark notification as read
     */
    @Override
    @Transactional
    public Notification markAsRead(Long notificationId) {
        try {
            return notificationRepository.findById(notificationId)
                    .map(notification -> {
                        notification.setStatus("READ");
                        notification.setReadDate(LocalDateTime.now());
                        return notificationRepository.save(notification);
                    })
                    .orElseThrow(() -> new InternalServiceError("Notification not found with ID: " + notificationId));
        } catch (Exception e) {
            throw new InternalServiceError("Error marking notification as read: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a notification
     */
    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        try {
            if (notificationRepository.existsById(notificationId)) {
                notificationRepository.deleteById(notificationId);
            } else {
                throw new InternalServiceError("Notification not found with ID: " + notificationId);
            }
        } catch (Exception e) {
            throw new InternalServiceError("Error deleting notification: " + e.getMessage(), e);
        }
    }
}
