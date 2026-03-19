package com.cognizant.greencity.controller;

import com.cognizant.greencity.entity.Notification;
import com.cognizant.greencity.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(
            @PathVariable Long notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Notification marked as read");
        response.put("data", notification);
        return ResponseEntity.ok(response);
    }

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
