package com.cognizant.greencity.controller;

import com.cognizant.greencity.entity.Notification;
import com.cognizant.greencity.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Notification Operations
 * Endpoints for retrieving and managing citizen notifications
 */
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

    /**
     * Get all notifications for a citizen
     * GET /api/notifications/citizen/{citizenId}
     *
     * @param citizenId - ID of the citizen
     * @return List of all notifications
     */
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<Map<String, Object>> getNotificationsByCitizenId(
            @PathVariable Long citizenId) {
        List<Notification> notifications = notificationService.getNotificationsByCitizenId(citizenId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Notifications retrieved successfully");
        response.put("citizenId", citizenId);
        response.put("count", notifications.size());
        response.put("data", notifications);
        return ResponseEntity.ok(response);
    }

    /**
     * Get unread notifications for a citizen
     * GET /api/notifications/citizen/{citizenId}/unread
     *
     * @param citizenId - ID of the citizen
     * @return List of unread notifications
     */
    @GetMapping("/citizen/{citizenId}/unread")
    public ResponseEntity<Map<String, Object>> getUnreadNotifications(
            @PathVariable Long citizenId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(citizenId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Unread notifications retrieved successfully");
        response.put("citizenId", citizenId);
        response.put("unreadCount", notifications.size());
        response.put("data", notifications);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark notification as read
     * PUT /api/notifications/{notificationId}/read
     *
     * @param notificationId - ID of the notification
     * @return Updated notification
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(
            @PathVariable Long notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Notification marked as read");
        response.put("data", notification);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete notification
     * DELETE /api/notifications/{notificationId}
     *
     * @param notificationId - ID of the notification
     * @return Success message
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Map<String, String>> deleteNotification(
            @PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification deleted successfully");
        response.put("notificationId", notificationId.toString());
        return ResponseEntity.ok(response);
    }
}
