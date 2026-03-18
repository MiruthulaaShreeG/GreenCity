package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.Notification;
import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    List<Notification> getNotificationsByCitizenId(Long citizenId);

    List<Notification> getUnreadNotifications(Long citizenId);

    Notification markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);
}
