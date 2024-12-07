package com.klef.jfsd.sdpproject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Notification.Role1;
import com.klef.jfsd.sdpproject.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService 
{
	 @Autowired
     private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(Notification notification) {
        // Simulate sending notification (prints to console)
        System.out.println("Sending notification: " + notification.getMesg() + " to " + notification.getRole());
    }

	/*
	 * public List<Notification> getNotificationsByRole(Role1 role) { return
	 * notificationRepository.findByRoleAndPostedTimeIsNotNull(role); }
	 */
        
        public List<Notification> getNotificationsByRole(Role1 role) {
            // Get the current time for filtering notifications
            LocalDateTime now = LocalDateTime.now();
            // Only fetch notifications for the specified role, with a posted time, and scheduled in the past
            return notificationRepository.findByRoleAndPostedTimeIsNotNullAndScheduledTimeBefore(role, now);
        }

}
